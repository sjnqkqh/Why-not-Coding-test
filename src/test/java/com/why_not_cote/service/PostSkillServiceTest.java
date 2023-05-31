package com.why_not_cote.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.why_not_cote.config.TestIsolationListener;
import com.why_not_cote.entity.post.HirePost;
import com.why_not_cote.repository.HirePostRepository;
import com.why_not_cote.repository.PostSkillRepository;
import com.why_not_cote.repository.SkillRepository;
import com.why_not_cote.repository.TestDataSet;
import java.util.List;
import org.hibernate.Hibernate;
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
class PostSkillServiceTest {
    @Autowired
    private HirePostRepository hirePostRepository;

    @Autowired
    private PostSkillRepository postSkillRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private PostSkillService postSkillService;

    @BeforeAll
    public void setHirePostTestData() {
        TestDataSet.insertPostTestData(
            hirePostRepository, postSkillRepository, skillRepository
        );
    }

    @Test
    @DisplayName("기술명으로 채용 공고 목록 조회 (채용 공고는 초기화 X)")
    public void getHirePostBySkillTitleWithoutInitialize(){
        // Given
        List<String> titleList = List.of("Java");

        // When
        List<HirePost> result = postSkillService.getPostSkillListByTitleList(titleList);

        // Then
        assertThat(result.size()).isEqualTo(2);
        for(HirePost item: result){
            assertThat(Hibernate.isInitialized(item)).isFalse();
        }

    }

}