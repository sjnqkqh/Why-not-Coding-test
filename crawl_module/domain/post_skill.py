from datetime import datetime

from peewee import *

from crawl_module.domain.base_model import BaseModel


class TbPostSkill(BaseModel):
    post_skill_id = AutoField()
    post_id = BigIntegerField()
    skill_id = BigIntegerField()
    created_at = DateTimeField(default=datetime.now)
    updated_at = DateTimeField(default=datetime.now)

    class Meta:
        table_name = "TB_POST_SKILL"
