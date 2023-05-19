# Why_not_cote

### 어째서 개발자 전용 채용 플랫폼에도 코딩 테스트/과제 전형을 보는 기업만 모아보는 기능은 없을까?

---

Why_not_cote 프로젝트는 사소한 불편함에서 시작된 1인 프로젝트입니다.

각종 유명 채용 플랫폼을 비롯하여, 개발자들을 위한 채용 플랫폼 사이트에서도 채용 과정에서 과제물 전형이나 코딩 테스트가 포함된 기업들을 모아 보는 기능은 제공되지 않았습니다.

코딩 테스트를 치르는 기업에 지원 하고 싶은 저로서는 기업들이 코딩 테스트를 진행하는지 확인하기 위해 채용 공고의 상세 페이지의 최하단까지 확인하여야 제가 원하는 정보를 찾을 수 있었습니다.

이런 과정을 조금 줄여보고자, 그리고 새로 학습한 내용들을 체화하고자 해당 프로젝트는 시작되었습니다.

### 사용 기술

---

- **Data crawling**
    - `Python 3.9`
    - `Peewee`
- **Server Side**
    - `Spring Boot 2.6.12`
    - `Java 11`
    - `Spring Data JPA`
    - `Elastic Search (후보)`
    - `Mysql 8.0`
    - `Mockito`
    - `RestDocs`

### 개발 진행 상황

---

1. 데이터 모델링
2. **데이터 크롤링 (Now!)**
3. 코딩 테스트/ 과제물 전형 유무 판별 로직 구현
4. 채용 공고 목록 검색/ 상세 조회 API 구현
5. 회원 기능 API 수정 (회원가입 및 휴대전화 인증 로직 개편 예정)
6. 채용 공고 관련 댓글, 북마크 기능 구현

### Part Description

---

[Data Crawlling](https://www.notion.so/Data-Crawlling-40a120b9b97b40a09757e772039649c6)
[DataBase](https://www.notion.so/DataBase-70251b29f4264deea4d3067e95992d21)
[회원 기능 API](https://www.notion.so/API-8f2520e9e2b34e75948f268370de23ce)
[채용 공고 API](https://www.notion.so/API-9866fe2e0554495ea04d7b4a424a111d)