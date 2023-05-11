import json
import logging

from peewee import MySQLDatabase, Model

with open('../config/config.json') as f:
    config = json.load(f)

logger = logging.getLogger('peewee')
logger.setLevel(logging.DEBUG)
logger.addHandler(logging.StreamHandler())

db = MySQLDatabase(
    database=config['database']['schema'],
    user=config['database']['user_id'],
    password=config['database']['password'],
    host=config['database']['host'],
    port=config['database']['port']
)


class BaseModel(Model):
    class Meta:
        database = db
