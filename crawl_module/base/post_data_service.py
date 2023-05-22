from crawl_module.domain.base_model import db
from crawl_module.domain.hiring_post import *
from crawl_module.domain.post_skill import TbPostSkill
from crawl_module.domain.skill import TbSkill
from crawl_module.dto.postDto import Post


@db.atomic()
def save_brief_hiring_post(post: Post):
    company_id = TbCompany.select().where(TbCompany.company_name == post.company_name)
    return TbHiringPost.get_or_create(
        company_id=company_id, post_title=post.post_title
    )[0]


# 채용 공고 상세 내용 저장
def save_post_detail(post_id, post_dto: Post):
    post = TbHiringPost.get_by_id(post_id)

    post.origin_post_id = post_dto.origin_post_id
    post.content = post_dto.post_content
    post.education = post_dto.education
    post.post_url = post_dto.post_url
    post.min_career = post_dto.min_career
    post.max_career = post_dto.max_career

    post.save()


# 채용 공고별 기술 스택 저장
@db.atomic()
def save_post_skills(post_id, skill_id_list: list):
    records = []
    for skill_id in skill_id_list:
        records.append({"post_id": post_id, "skill_id": skill_id})

    TbPostSkill.insert_many(records).execute()


# DB에 등록되지 않은 회사명 등록 처리
@db.atomic()
def save_not_exist_company_with_company_name(company_names):
    query = TbCompany.select().where(TbCompany.company_name.in_(company_names))

    exists = set([item.company_name for item in query])
    not_exists = [
        {"company_name": company_name}
        for company_name in company_names
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


