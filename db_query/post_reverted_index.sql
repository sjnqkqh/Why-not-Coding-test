# 기존 풀텍스트 인덱스 제거
drop index HIRING_PROCESS_FULLTEXT_IDX on TB_HIRING_POST;

# Stopwords 테이블 생성
CREATE TABLE custom_stopword
(
    value VARCHAR(50)
);

# Stopwords 입력
insert into custom_stopword
values ('서류전형'),
       ('있습니다'),
       ('최종합격'),
       ('인터뷰'),
       ('정규직'),
       ('3개월'),
       ('채용절차'),
       ('절차는'),
       ('취소될'),
       ('이력서'),
       ('포트폴리오'),
       ('마감될'),
       ('3개월의'),
       ('기간이');

# innodb Stopwords 테이블과 커스텀 Stopwords 테이블 연동
set global innodb_ft_server_stopword_table = 'why_not_cote/custom_stopword';
-- 모두 소문자

# Fulltext Index 생성
CREATE FULLTEXT INDEX HIRING_PROCESS_FULLTEXT_IDX ON TB_HIRING_POST (recruitment_process);