package com.why_not_cote.config;

import com.why_not_cote.entity.post.HirePost;
import com.why_not_cote.entity.post.PostSkill;
import com.why_not_cote.entity.post.Skill;
import com.why_not_cote.repository.HirePostRepository;
import com.why_not_cote.repository.PostSkillRepository;
import com.why_not_cote.repository.SkillRepository;
import com.why_not_cote.util.code.YnCode;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

public class TestIsolationListener extends AbstractTestExecutionListener {

    @Override
    public void beforeTestClass(TestContext testContext) throws Exception {
        HirePostRepository hirePostRepository = testContext.getApplicationContext()
            .getBean("hirePostRepository", HirePostRepository.class);
        PostSkillRepository postSkillRepository = testContext.getApplicationContext()
            .getBean("postSkillRepository", PostSkillRepository.class);
        SkillRepository skillRepository = testContext.getApplicationContext()
            .getBean("skillRepository", SkillRepository.class);

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
            .jobCategory("서버/백엔드 개발자")
            .postUrl("URL 1")
            .build();

        HirePost kotlinSpringHirePostWithCodingTest = HirePost.builder()
            .postId(2L)
            .originPostId(2L)
            .postTitle("Back End 채용 (코테O)")
            .codingTestYn(YnCode.Y)
            .assignmentYn(YnCode.N)
            .jobCategory("서버/백엔드 개발자")
            .postUrl("URL 2")
            .build();

        HirePost nodeHirePostWithAssignment = HirePost.builder()
            .postId(3L)
            .originPostId(3L)
            .postTitle("Full-stack 채용 (과제 O)")
            .codingTestYn(YnCode.N)
            .assignmentYn(YnCode.Y)
            .jobCategory("웹 풀스택 개발자")
            .postUrl("URL 3")
            .build();

        hirePostRepository.saveAll(
            List.of(
                javaSrpingHirePost,
                kotlinSpringHirePostWithCodingTest,
                nodeHirePostWithAssignment
            )
        );

        System.out.println("hirePostRepository.findAll()"+hirePostRepository.findAll());

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

    @Override
    public void afterTestClass(final TestContext testContext) {
        final JdbcTemplate jdbcTemplate = getJdbcTemplate(testContext);
        final List<String> truncateQueries = getTruncateQueries(jdbcTemplate);
        truncateTables(jdbcTemplate, truncateQueries);
    }

    private List<String> getTruncateQueries(final JdbcTemplate jdbcTemplate) {
        return jdbcTemplate.queryForList(
            "SELECT Concat('TRUNCATE TABLE ', TABLE_NAME, ';') AS q FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC'",
            String.class);
    }

    private JdbcTemplate getJdbcTemplate(final TestContext testContext) {
        return testContext.getApplicationContext().getBean(JdbcTemplate.class);
    }

    private void truncateTables(final JdbcTemplate jdbcTemplate,
        final List<String> truncateQueries) {
        execute(jdbcTemplate, "SET REFERENTIAL_INTEGRITY FALSE");
        truncateQueries.forEach(v -> execute(jdbcTemplate, v));
        execute(jdbcTemplate, "SET REFERENTIAL_INTEGRITY TRUE");
    }

    private void execute(final JdbcTemplate jdbcTemplate, final String query) {
        jdbcTemplate.execute(query);
    }
}
