import json
import shutil
import uuid

import requests as requests
from yarl import URL

from crawl_module.base.post_data_service import *

with open("../config/config.json") as f:
    config = json.load(f)


# 채용 공고 검색 API 호출
def parse_post_search_api():
    url = config["api_url"]["search"]
    response = requests.get(url)

    # 요청에 성공한 경우, response.json() 메서드를 사용하여 응답 데이터를 파싱합니다.
    if response.status_code == 200:
        data = response.json()
        return data.get("result").get("positions")
    else:
        print(f"Request failed with status code Status:{response.status_code}")


# 채용 공고 Json -> PostDto
def convert_brief_post_to_dto(brief_post_json: dict):
    return Post(
        company_name=brief_post_json.get("companyName"),
        post_title=brief_post_json.get("title"),
        min_career=brief_post_json.get("minCareer"),
        max_career=brief_post_json.get("maxCareer"),
        tech_stacks=brief_post_json.get("techStacks"),
        locations=brief_post_json.get("locations"),
        post_url=URL(config["api_url"]["detail"]).with_path(
            "/" + str(brief_post_json.get("id"))
        ),
    )


# 채용 공고 이미지 다운로드
def download_image(url):
    filename = "image_" + str(uuid.uuid4()) + ".jpg"

    response = requests.get(url, stream=True)
    if response.status_code == 200:
        with open(filename, "wb") as f:
            response.raw.decode_content = True
            shutil.copyfileobj(response.raw, f)


# 채용 공고 목록에서 회사 리스트 추출
def get_company_name_list(position_list):
    company_name_list = []
    for position in position_list:
        company_name_list.append(position.get("companyName"))

    return set(company_name_list)


# 채용 공고 목록에서 기술 스택 리스트 추출
def get_skill_list(position_list):
    skill_list = []
    for position in position_list:
        for item in position.get("techStacks"):
            skill_list.append(item)

    return set(skill_list)


# 채용 공고 목록 조회 및 저장
def save_positions():
    positions = parse_post_search_api()
    company_name_list = get_company_name_list(positions)
    skill_list = get_skill_list(positions)

    # DB에 등록되어 있지 않은 회사 및 기술 스택 저장
    save_not_exist_company_with_company_name(company_name_list)
    save_not_exist_skills(skill_list)

    # 채용 공고 저장
    for item in positions:
        post = convert_brief_post_to_dto(item)
        save_brief_hiring_post(post)


save_positions()