from crawl_module.domain.base_model import db
from crawl_module.domain.hiring_post import *
from crawl_module.domain.skill import TbSkill
from crawl_module.dto.postDto import Post


@db.atomic()
def save_brief_hiring_post(post: Post):
    company_id = TbCompany.select().where(TbCompany.company_name == post.company_name)
    return TbHiringPost.get_or_create(
        company_id=company_id,
        post_title=post.post_title,
        job_category=post.job_category,
    )[0]


# 채용 공고 상세 내용 저장
def save_post_detail(post_id, post_dto: Post):
    post = TbHiringPost.get_by_id(post_id)

    post.origin_post_id = post_dto.origin_post_id
    post.content = post_dto.post_content
    post.recruitment_process = post_dto.recruitment_process
    post.education = post_dto.education
    post.post_url = post_dto.post_url
    post.min_career = post_dto.min_career
    post.max_career = post_dto.max_career

    post.save()


# 채용 공고별 기술 스택 저장
@db.atomic()
def save_post_skills(post_id, skill_id_list: list):
    query = (
        f"INSERT INTO TB_POST_SKILL (POST_ID, SKILL_ID) "
        f"SELECT {post_id} AS POST_ID, B.SKILL_ID "
        f"FROM TB_POST_SKILL A "
        f"RIGHT JOIN TB_SKILL B "
        f"ON A.SKILL_ID = B.SKILL_ID AND A.POST_ID = {post_id} "
        f"WHERE "
        f" A.SKILL_ID IS NULL "
    )

    if len(skill_id_list) == 1:
        query += f"AND B.SKILL_ID = {skill_id_list[0]}"
    else:
        query += f"AND B.SKILL_ID IN {tuple(skill_id_list)} "

    db.execute_sql(query, commit=True)


# DB에 등록되지 않은 회사명 등록 처리
@db.atomic()
def save_not_exist_company_with_brief_company(company_dict: dict):
    company_name_list = list(company_dict.keys())

    query = TbCompany.select().where(TbCompany.company_name.in_(company_name_list))

    exists = set([item.company_name for item in query])
    not_exists = [
        {
            "company_name": company_name,
            "origin_company_id": company_dict.get(company_name),
        }
        for company_name in company_name_list
        if company_name not in exists
    ]

    TbCompany.insert_many(not_exists).execute()


# DB에 등록되지 않은 기술명 등록 처리
@db.atomic()
def save_not_exist_skills(stacks):
    query = TbSkill.select().where(TbSkill.title.in_(stacks))

    exists = set([item.title for item in query])
    not_exists = [{"title": skill} for skill in stacks if skill not in exists]

    TbSkill.insert_many(not_exists).execute()


# 기술명에 따른 ID값 반환
@db.atomic()
def get_skills_by_names(stacks):
    query = TbSkill.select().where(TbSkill.title.in_(stacks))
    return [item.skill_id for item in query]
