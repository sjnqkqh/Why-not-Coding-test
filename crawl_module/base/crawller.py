import json
import shutil
import uuid

import requests as requests


# 채용 공고 검색 API 파싱
def parse_post_search_api():
    url = config['api_url']['search']
    response = requests.get(url)

    if response.status_code == 200:
        # 요청에 성공한 경우, response.json() 메서드를 사용하여 응답 데이터를 파싱합니다.
        data = response.json()
        return data.get('result').get('positions')

    else:
        print(f'Request failed with status code Status:{response.status_code}')


# 채용 공고 이미지 다운로드
def download_image(url):
    filename = "image_" + str(uuid.uuid4()) + ".jpg"

    response = requests.get(url, stream=True)
    if response.status_code == 200:
        with open(filename, "wb") as f:
            response.raw.decode_content = True
            shutil.copyfileobj(response.raw, f)


with open('../config/config.json') as f:
    config = json.load(f)
positions = parse_post_search_api()
download_image(positions[0].get('imagePath'))
