import json
import urllib.parse

from bs4 import BeautifulSoup
from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.support.ui import WebDriverWait


# Chrome 기반 드라이버 반환
def create_browser_driver(driver_location):
    service = Service(driver_location)
    options = webdriver.ChromeOptions()
    options.add_argument('--headless')
    return webdriver.Chrome(service=service, options=options)


def crawl_with_scroll_search_page(driver, target_url: str):
    # 모든 채용 공고 로딩을 위한 스크롤
    # FIXME : 스크롤 로딩 작동 안 함
    driver.get(target_url)
    last_height = driver.execute_script('return document.body.scrollHeight')
    while True:
        driver.execute_script('window.scrollTo(0, document.body.scrollHeight);')
        WebDriverWait(driver, 10).until(lambda d: d.execute_script('return document.readyState') == 'complete')
        new_height = driver.execute_script('return document.body.scrollHeight')
        if new_height == last_height:
            break
        last_height = new_height

    # 페이지 소스코드 추출
    soup = BeautifulSoup(driver.page_source, 'html.parser')

    # 채용 별 div 요소 클래스 명 추출
    jobs = []
    job_section = soup.select('main section')[2]
    card_class_name = job_section.find(recursive=False).get('class')[-1]
    job_cards = soup.find_all('div', {'class': card_class_name}) # 채용 공고 목록의 클래스명 (유동적)

    for job_card in job_cards:
        div = job_card.find(recursive=False).find_all(recursive=False)[-1]
        job = {
            'title': job_card.find('h2', {'class': 'position_card_info_title'}).text.strip(),
            'company':  div.find(recursive=False).text.strip(),
            'location': div.find_all('ul', recursive=False)[-1].find(recursive=False).text.strip(),
            'url': "https://" + urllib.parse.urlsplit(target_url).hostname + job_card.find('a')['href']
        }
        jobs.append(job)

    return jobs


def crawl_hiring_post_detail_page(driver, target_url: str):
    driver.get(target_url)
    soup = BeautifulSoup(driver.page_source, 'html.parser')
    context = soup.select('position_info dl')
    print(context)
    return context[1:]


with open('../config/config.json') as f:
    config = json.load(f)
driver = create_browser_driver(config['driver']['location'])
jobs = crawl_with_scroll_search_page(driver, config['crawler']['url'])
for item in jobs:
    print(item)

# for job in jobs:
#     description = crawl_hiring_post_detail_page(driver, job['url'])
#     print(description)
