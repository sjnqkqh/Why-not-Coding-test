from datetime import datetime

from peewee import *


class TbCompany(Model):
    company_id = BigAutoField()
    company_name = CharField(max_length=255)
    industry_type = CharField(max_length=255)
    company_type = CharField(max_length=255)
    employee_count = IntegerField(null=True)
    address = CharField(max_length=255, null=True)
    homepage = CharField(max_length=255, null=True)
    phone_number = CharField(max_length=255, null=True)
    thumbnail_url = CharField(max_length=255, null=True)
    created_at = DateTimeField(default=datetime.datetime.now)
    updated_at = DateTimeField(default=datetime.datetime.now)

    class Meta:
        db_table = 'TB_COMPANY'
