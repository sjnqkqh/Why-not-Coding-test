from datetime import datetime

from peewee import *

from crawl_module.domain.base_model import *


class TbSkill(BaseModel):
    skill_id = BigAutoField()
    title = CharField(max_length=255, null=False)
    created_at = DateTimeField(default=datetime.now())
    updated_at = DateTimeField(default=datetime.now())

    class Meta:
        db_table = 'TB_SKILL'
