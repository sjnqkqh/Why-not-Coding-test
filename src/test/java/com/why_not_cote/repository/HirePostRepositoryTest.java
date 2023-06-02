package com.why_not_cote.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.why_not_cote.config.DataIsolateTest;
import com.why_not_cote.entity.post.HirePost;
import com.why_not_cote.entity.post.PostSkill;
import com.why_not_cote.entity.post.Skill;
import com.why_not_cote.util.code.YnCode;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


@DataIsolateTest
public class HirePostRepositoryTest {

    @Autowired
    private HirePostRepository hirePostRepository;

    @Autowired
    private PostSkillRepository postSkillRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private HirePostRepositoryCustom hirePostRepositoryCustom;

    @BeforeAll
    public void setHirePostTestData() {
        TestDataSet.insertPostTestData(
            hirePostRepository, postSkillRepository, skillRepository
        );
    }

    // FIXME 테스트 코드 안정화 이후 제거 예정
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

    @Test
    @DisplayName("직무 카테고리에 따른 공고 목록 검색")
    @Transactional(readOnly = true)
    public void testFindHirePostByJobCategory() {
        // Given
        List<String> jobCategoryList = List.of("웹 풀스택 개발자");

        // When
        List<HirePost> postList =
            hirePostRepositoryCustom.getHirePostListBySkillName(
                null,
                jobCategoryList,
                null,
                null);

        // Then
        assertThat(postList.size()).isEqualTo(1);
        assertThat(postList.get(0).getPostId()).isEqualTo(3L);
    }

    @Test
    @DisplayName("코딩 테스트 유무에 따른 공고 목록 검색")
    @Transactional(readOnly = true)
    public void testFindHirePostByCodingTestYn() {
        // Given
        YnCode codingTestY = YnCode.Y;
        YnCode codingTestN = YnCode.N;

        // When
        List<HirePost> codingTestPostList = hirePostRepositoryCustom.getHirePostListBySkillName(
            null, null, codingTestY, null);
        List<HirePost> noCodingTestPostList = hirePostRepositoryCustom.getHirePostListBySkillName(
            null, null, codingTestN, null);

        for (HirePost item : codingTestPostList) {
            System.out.println("item = " + item);
        }

        // Then
        assertThat(codingTestPostList.size()).isEqualTo(1);
        assertThat(codingTestPostList.get(0).getPostId()).isEqualTo(2);

        assertThat(noCodingTestPostList.size()).isEqualTo(2);
        assertThat(noCodingTestPostList.get(0).getPostId()).isEqualTo(1);
        assertThat(noCodingTestPostList.get(1).getPostId()).isEqualTo(3);
    }

    @Test
    @DisplayName("과제 전형 유무에 따른 공고 목록 검색")
    @Transactional(readOnly = true)
    public void testFindHirePostByAssignmentYn() {
        // Given
        YnCode assignmentY = YnCode.Y;
        YnCode assignmentN = YnCode.N;

        // When
        List<HirePost> assignmentPostList = hirePostRepositoryCustom.getHirePostListBySkillName(
            null, null, null, assignmentY);
        List<HirePost> noAssignmentPostList = hirePostRepositoryCustom.getHirePostListBySkillName(
            null, null, null, assignmentN);

        // Then
        assertThat(assignmentPostList.size()).isEqualTo(1);
        assertThat(assignmentPostList.get(0).getPostId()).isEqualTo(3);

        assertThat(noAssignmentPostList.size()).isEqualTo(2);
        assertThat(noAssignmentPostList.get(0).getPostId()).isEqualTo(1);
        assertThat(noAssignmentPostList.get(1).getPostId()).isEqualTo(2);
    }

    @Test
    @DisplayName("빈 카테고리 리스트로 채용 공고 검색")
    public void testSearchWithEmptyJobCategoryAndEmptySkillList() {
        // Given
        List<String> jobCategory = new ArrayList<>();

        // When
        List<HirePost> postList = hirePostRepositoryCustom.getHirePostListBySkillName(null,
            jobCategory, null, null);

        // Then
        assertThat(postList.size()).isEqualTo(3);
    }
}