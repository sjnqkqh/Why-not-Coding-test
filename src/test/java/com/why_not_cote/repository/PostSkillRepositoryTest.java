package com.why_not_cote.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.why_not_cote.config.TestIsolationListener;
import com.why_not_cote.entity.post.HirePost;
import com.why_not_cote.entity.post.PostSkill;
import com.why_not_cote.entity.post.Skill;
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
    @DisplayName("데이터 세팅값 확인")
    @Transactional(readOnly = true)
    public void testFindAllSkill() {
        // When
        List<HirePost> postList = hirePostRepository.findAll();
        List<Skill> skillList = skillRepository.findAll();
        List<PostSkill> postSkillList = postSkillRepository.findAll();

        // Do
        System.out.println("======= 채용 공고 목록 =======");
        for (HirePost item : postList) {
            System.out.println(item);
        }

        System.out.println("======= 기술 스택 목록 =======");
        for (Skill item : skillList) {
            System.out.println(item);
        }

        // Then
        assertThat(postList.size()).isEqualTo(3);
        assertThat(skillList.size()).isEqualTo(4);
        assertThat(postSkillList.size()).isEqualTo(6);
    }
}