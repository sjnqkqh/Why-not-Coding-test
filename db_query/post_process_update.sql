UPDATE
    TB_HIRING_POST
SET coding_test_exist_yn = 'Y'
WHERE MATCH(recruitment_process) AGAINST('코딩*' IN BOOLEAN MODE)
   OR MATCH(recruitment_process) AGAINST('테스트*' IN BOOLEAN MODE)
   OR MATCH(recruitment_process) AGAINST('coding*' IN BOOLEAN MODE)
   OR MATCH(recruitment_process) AGAINST('test*' IN BOOLEAN MODE);

UPDATE
    TB_HIRING_POST
SET assignment_exist_yn = 'Y'
WHERE MATCH(recruitment_process) AGAINST('과제*' IN BOOLEAN MODE)
   OR MATCH(recruitment_process) AGAINST('사전과제*' IN BOOLEAN MODE);