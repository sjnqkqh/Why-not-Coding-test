from crawl_module.domain.base_model import db
from crawl_module.domain.hiring_post import *
from crawl_module.domain.skill import TbSkill


def save_brief_company_info_by_company_name(company_name):
    return TbCompany.get_or_create(company_name=company_name)


@db.atomic()
def save_brief_hiring_post(post: dict):
    company_name = post.get('companyName')
    company = save_brief_company_info_by_company_name(company_name)[0]
    TbHiringPost.get_or_create(company_id=company, post_name=post.get('title'))
    save_not_exist_tech_stacks(post.get('techStacks'))


# 채용 공고 상세 내용 저장

# 채용 공고 기술 스택 관리
@db.atomic()
def save_not_exist_tech_stacks(stacks):
    query = TbSkill.select().where(TbSkill.title.in_(stacks))

    exists = set([item.title for item in query])
    not_exists = [{'title': skill} for skill in stacks if skill not in exists]

    TbSkill.insert_many(not_exists).execute()

# 채용 공고 대표 이미지 관리
