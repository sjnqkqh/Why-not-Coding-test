from datetime import datetime

from peewee import *

from crawl_module.domain.base_model import BaseModel
from crawl_module.domain.company import TbCompany


class TbHiringPost(BaseModel):
    post_id = BigAutoField()
    origin_post_id = BigIntegerField(null=True)
    company_id = ForeignKeyField(TbCompany, backref="hiring_posts")
    job_category = CharField(max_length=255, null=True)
    post_title = CharField(max_length=255)
    content = TextField(null=True)
    recruitment_process = TextField(null=True)
    min_career = CharField(max_length=255, null=True)
    max_career = CharField(max_length=255, null=True)
    education = CharField(max_length=255, null=True)
    employment_type = CharField(max_length=255, null=True)
    working_region = CharField(max_length=255, null=True)
    coding_test_exist_yn = CharField(max_length=1, default="N")
    assignment_exist_yn = CharField(max_length=1, default="N")
    only_image_yn = CharField(max_length=1, default="N")
    admin_checked_yn = CharField(max_length=1, default="N")
    post_url = CharField(max_length=255)
    created_at = DateTimeField(default=datetime.now)
    updated_at = DateTimeField(default=datetime.now)

    class Meta:
        db_table = "TB_HIRING_POST"
