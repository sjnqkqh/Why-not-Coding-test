from crawl_module.domain.base_model import db
from crawl_module.domain.hiring_post import *
from crawl_module.domain.skill import TbSkill
from crawl_module.dto.postDto import Post


@db.atomic()
def save_brief_hiring_post(post: Post):
    company_id = TbCompany.select().where(TbCompany.company_name == post.company_name)
    TbHiringPost.get_or_create(company_id=company_id, post_title =post.post_title)


# 채용 공고 상세 내용 저장


# 채용 공고에 따른 회사명 관리
@db.atomic()
def save_not_exist_company_with_company_name(company_names):
    query = TbCompany.select().where(TbCompany.company_name.in_(company_names))

    exists = set([item.company_name for item in query])
    print(exists)
    not_exists = [
        {"company_name": company_name}
        for company_name in company_names
        if company_name not in exists
    ]

    TbCompany.insert_many(not_exists).execute()


# 채용 공고 기술 스택 관리
@db.atomic()
def save_not_exist_skills(stacks):
    query = TbSkill.select().where(TbSkill.title.in_(stacks))

    exists = set([item.title for item in query])
    not_exists = [{"title": skill} for skill in stacks if skill not in exists]

    TbSkill.insert_many(not_exists).execute()


# 채용 공고 대표 이미지 관리
