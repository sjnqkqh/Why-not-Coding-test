import json
import logging
import shutil
import time
import uuid

import requests as requests
from yarl import URL

from crawl_module.base.post_data_service import *

with open("../config/config.json") as f:
    config = json.load(f)


# 채용 공고 검색 API 호출
def crawl_post_list(page):
    url = URL(config["jp"]["search_api"]).update_query({"page": page})
    response = requests.get(url)

    # 요청에 성공한 경우, response.json() 메서드를 사용하여 응답 데이터를 파싱합니다.
    if response.status_code == 200:
        data = response.json()
        return data.get("result").get("positions")
    else:
        print(f"Request failed with status code Status:{response.status_code}")


# 채용 상세 정보 API 호출
def crawl_post_detail(url):
    response = requests.get(url)

    # 요청에 성공한 경우, response.json() 메서드를 사용하여 응답 데이터를 파싱합니다.
    if response.status_code == 200:
        data = response.json()
        return data.get("result")
    else:
        print(f"Request failed with status code Status:{response.status_code}")


# 채용 공고 Json -> PostDto
def convert_brief_post_to_dto(brief_post_json: dict):
    return Post(
        company_name=brief_post_json.get("companyName"),
        job_category=brief_post_json.get("jobCategory"),
        post_title=brief_post_json.get("title"),
        min_career=brief_post_json.get("minCareer"),
        max_career=brief_post_json.get("maxCareer"),
        tech_stacks=brief_post_json.get("techStacks"),
        locations=brief_post_json.get("locations"),
        post_url=URL(config["jp"]["detail_page"]).joinpath(
            str(brief_post_json.get("id"))
        ),
        origin_post_id=brief_post_json.get("id"),
    )


# 채용 공고 상세 정보 추가
def convert_post_json_to_dto(post_json: dict):
    post = convert_brief_post_to_dto(post_json)
    post.post_content = (
        post_json.get("responsibility")
        + post_json.get("qualifications")
        + post_json.get("preferredRequirements")
        + post_json.get("welfares")
        + post_json.get("recruitProcess")
    )
    post.recruitment_process = post_json.get("recruitProcess")
    post.min_career = post_json.get("minCareer")
    post.max_career = post_json.get("maxCareer")
    post.education = post_json.get("education")

    return post


# 채용 공고 이미지 다운로드
def download_image(url):
    filename = "image_" + str(uuid.uuid4()) + ".jpg"

    response = requests.get(url, stream=True)
    if response.status_code == 200:
        with open(filename, "wb") as f:
            response.raw.decode_content = True
            shutil.copyfileobj(response.raw, f)


# 채용 공고 목록에서 회사 리스트 추출
def get_company_list(position_list):
    company_dict = dict()
    for position in position_list:
        company_dict[position.get("companyName")] = position.get("companyProfileId")

    return company_dict


# 채용 공고 목록에서 기술 스택 리스트 추출
def get_skill_list(position_list):
    skill_list = []
    for position in position_list:
        skill_list.append(position.get("stack"))

    return set(skill_list)


# 채용 공고 목록 조회 및 저장
@db.atomic()
def save_positions(page):
    positions = crawl_post_list(page)
    company_dict = get_company_list(positions)

    # DB에 없는 회사 등록
    save_not_exist_company_with_brief_company(company_dict)

    # 채용 공고 저장
    time.sleep(1)
    post_id_list = []
    for item in positions:
        # 간략한 채용 공고 정보 저장
        post = convert_brief_post_to_dto(item)
        post_id = save_brief_hiring_post(post)
        post_id_list.append(post_id)

        # 자세한 채용 공고 정보 저장
        detail_post_json = crawl_post_detail(
            URL(config["jp"]["detail_api"]).joinpath(str(post.origin_post_id))
        )
        detail_post_dto = convert_post_json_to_dto(detail_post_json)
        save_post_detail(post_id=post_id, post_dto=detail_post_dto)

        # Tech Stack 등록
        skill_list = get_skill_list(detail_post_dto.tech_stacks)
        save_not_exist_skills(skill_list)  # FIXME 이거랑 아래 줄 합쳐서 한 번에 처리 가능할듯
        skill_id_list = get_skills_by_names(skill_list)
        save_post_skills(post_id, skill_id_list)

        time.sleep(1)


# 로그 생성
logger = logging.getLogger()

# 로그의 출력 기준 설정
logger.setLevel(logging.DEBUG)

# log 출력 형식
formatter = logging.Formatter("%(asctime)s - %(name)s - %(levelname)s - %(message)s")

# log 출력
stream_handler = logging.StreamHandler()
stream_handler.setFormatter(formatter)
logger.addHandler(stream_handler)

# log를 파일에 출력
file_handler = logging.FileHandler(datetime.now().strftime("%Y-%m-%d")+"_crawl.log")
file_handler.setFormatter(formatter)
logger.addHandler(file_handler)

for i in range(179, 180):
    try:
        save_positions(i)
    except:
        logger.error(f"{i}번째 페이지 크롤링 에러 발생")
    time.sleep(5)
