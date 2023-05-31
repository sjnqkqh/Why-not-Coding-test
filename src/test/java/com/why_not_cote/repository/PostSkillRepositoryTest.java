package com.why_not_cote.repository;


import static org.assertj.core.api.Assertions.assertThat;

import com.why_not_cote.config.TestIsolationListener;
import com.why_not_cote.entity.post.PostSkill;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.transaction.annotation.Transactional;

@TestInstance(value = Lifecycle.PER_CLASS)
@SpringBootTest(properties = "spring.config.location=classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@TestExecutionListeners(
    value = {TestIsolationListener.class},
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS
)
class PostSkillRepositoryTest {

    @Autowired
    private HirePostRepository hirePostRepository;

    @Autowired
    private PostSkillRepository postSkillRepository;

    @Autowired
    private SkillRepository skillRepository;


    @BeforeAll
    public void setHirePostTestData() {
        TestDataSet.insertPostTestData(
            hirePostRepository, postSkillRepository, skillRepository
        );
    }

    @Test
    @DisplayName("기술명에 따른 공고-스킬 매핑 데이터 조회")
    @Transactional(readOnly = true)
    public void testFindPostSkillListBySkillTitleList() {
        // Given
        List<String> titleList = List.of("Java");

        // When
        List<PostSkill> result = postSkillRepository.findPostSkillBySkill_TitleInOrderByPostSkillId(titleList);

        // Then
        assertThat(result.size()).isEqualTo(2);
    }


    // TODO: 채용 공고 검색 관련 테스트 추가
}