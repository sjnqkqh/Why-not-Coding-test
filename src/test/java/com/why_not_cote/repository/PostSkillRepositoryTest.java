package com.why_not_cote.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.why_not_cote.entity.post.HirePost;
import com.why_not_cote.entity.post.PostSkill;
import com.why_not_cote.entity.post.Skill;
import com.why_not_cote.util.code.YnCode;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(properties = "spring.config.location=classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class PostSkillRepositoryTest {

    @Autowired
    private HirePostRepository hirePostRepository;

    @Autowired
    private PostSkillRepository postSkillRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private HirePostRepositoryCustom hirePostRepositoryCustom;

    @BeforeAll
    @Rollback(value = false)
    public static void setTestPostSkillData(
        @Autowired SkillRepository skillRepository,
        @Autowired PostSkillRepository postSkillRepository,
        @Autowired HirePostRepository hirePostRepository
    ) {
        // 스킬 목록 세팅
        Skill java = new Skill(1L, "Java");
        Skill spring = new Skill(2L, "Spring Framework");
        Skill node = new Skill(3L, "Node.js");
        Skill kotlin = new Skill(4L, "Kotlin");
        skillRepository.saveAll(List.of(java, spring, node, kotlin));

        // 채용 공고 세팅
        HirePost javaSrpingHirePost = HirePost.builder()
            .postId(1L)
            .originPostId(1L)
            .postTitle("Back End 채용 (코테, 과제 X)")
            .postUrl("URL 1")
            .build();

        HirePost kotlinSpringHirePostWithCodingTest = HirePost.builder()
            .postId(2L)
            .originPostId(2L)
            .postTitle("Back End 채용 (코테O)")
            .codingTestYn(YnCode.Y)
            .assignmentYn(YnCode.N)
            .postUrl("URL 2")
            .build();

        HirePost nodeHirePostWithAssignment = HirePost.builder()
            .postId(3L)
            .originPostId(3L)
            .postTitle("Back End 채용 (과제 O)")
            .codingTestYn(YnCode.Y)
            .assignmentYn(YnCode.N)
            .postUrl("URL 3")
            .build();

        hirePostRepository.saveAll(
            List.of(
                javaSrpingHirePost,
                kotlinSpringHirePostWithCodingTest,
                nodeHirePostWithAssignment
            )
        );

        // 채용 공고별 스킬 매핑
        // Java+Spring
        postSkillRepository.save(new PostSkill(javaSrpingHirePost, java));
        postSkillRepository.save(new PostSkill(javaSrpingHirePost, spring));

        // Kotlin+Spring
        postSkillRepository.save(new PostSkill(kotlinSpringHirePostWithCodingTest, kotlin));
        postSkillRepository.save(new PostSkill(kotlinSpringHirePostWithCodingTest, spring));
        postSkillRepository.save(new PostSkill(kotlinSpringHirePostWithCodingTest, java));

        // NodeJS
        postSkillRepository.save(new PostSkill(nodeHirePostWithAssignment, node));
    }

    @Test
    @DisplayName("데이터 세팅값 확인")
    @Transactional(readOnly = true)
    public void testFindAllSkill() {
        // When
        List<HirePost> postList = hirePostRepository.findAll();
        List<Skill> skillList = skillRepository.findAll();
        List<PostSkill> postSkillList = postSkillRepository.findAll();

        // Then
        assertThat(postList.size()).isEqualTo(3);
        assertThat(skillList.size()).isEqualTo(4);
        assertThat(postSkillList.size()).isEqualTo(6);
    }

    @Test
    @DisplayName("특정 기술명에 따른 채용 공고 조회 테스트")
    @Transactional(readOnly = true)
    public void testFindHirePostBySkillName(){
        // Given
        String skillName = "Java";

        // When
        System.out.println("hirePostRepositoryCustom = " + hirePostRepositoryCustom);
        List<HirePost> postList = hirePostRepositoryCustom.getHirePostListBySkillName(List.of(skillName));

        // Then
        assertThat(postList.size()).isEqualTo(2);
    }

}