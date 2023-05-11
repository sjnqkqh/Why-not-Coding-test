# 기업 정보 저장 (brief)
from crawl_module.domain.base_model import db
from crawl_module.domain.company import *
from crawl_module.domain.hiring_post import *


def save_brief_company_info_by_company_name(company_name):
    return TbCompany.get_or_create(company_name=company_name)


# 채용 공고 간략한 내용 저장
@db.atomic()
def save_brief_hiring_post(post: dict):
    company_name = post.get('companyName')
    company = save_brief_company_info_by_company_name(company_name)[0]
    TbHiringPost.get_or_create(company_id=company, post_name=post.get('title'))

# 채용 공고 상세 내용 저장

# 채용 공고 기술 스택 관리

# 채용 공고 대표 이미지 관리
