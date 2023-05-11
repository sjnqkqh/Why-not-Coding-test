from datetime import datetime

from peewee import *

from crawl_module.domain.base_model import BaseModel
from crawl_module.domain.hiring_post import TbHiringPost


class TbPostImage(BaseModel):
    post_image_id = BigAutoField()
    post_id = ForeignKeyField(TbHiringPost, backref='post_images')
    image_url = CharField(max_length=255)
    image_order = IntegerField()
    created_at = DateTimeField(default=datetime.now)
    updated_at = DateTimeField(default=datetime.now)

    class Meta:
        db_table = 'TB_POST_IMAGE'
