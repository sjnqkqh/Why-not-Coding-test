from datetime import datetime

from peewee import *

from crawl_module.domain.base_model import BaseModel
from crawl_module.domain.company import TbCompany


class TbHiringPost(BaseModel):
    post_id = BigAutoField()
    company_id = ForeignKeyField(TbCompany, backref="hiring_posts")
    post_name = CharField(max_length=255)
    qualifications_career = CharField(max_length=255, null=True)
    qualifications_education = CharField(max_length=255, null=True)
    qualifications_skill = CharField(max_length=255, null=True)
    working_condition_employment_type = CharField(max_length=255, null=True)
    working_condition_region = CharField(max_length=255, null=True)
    coding_test_exist_yn = CharField(max_length=1, default="N")
    assignment_exist_yn = CharField(max_length=1, default="N")
    only_image_yn = CharField(max_length=1, default="N")
    admin_checked_yn = CharField(max_length=1, default="N")
    post_url = CharField(max_length=255)
    created_at = DateTimeField(default=datetime.now)
    updated_at = DateTimeField(default=datetime.now)

    class Meta:
        db_table = "TB_HIRING_POST"
