package com.why_not_cote.repository;

import static com.why_not_cote.util.PageableUtils.createPageable;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.why_not_cote.config.DataIsolateTest;
import com.why_not_cote.entity.post.HirePost;
import com.why_not_cote.util.code.YnCode;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


@DataIsolateTest
public class HirePostRepositoryTest {

    @Autowired
    private HirePostRepositoryCustom hirePostRepositoryCustom;


    @Test
    @DisplayName("직무 카테고리에 따른 공고 목록 검색")
    @Transactional(readOnly = true)
    public void testFindHirePostByJobCategory() {
        // Given
        List<String> jobCategoryList = List.of("웹 풀스택 개발자");

        // When
        List<HirePost> postList =
            hirePostRepositoryCustom.getHirePostListToSearch(
                null,
                jobCategoryList,
                null,
                null, createPageable(0, 0));

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
        List<HirePost> codingTestPostList = hirePostRepositoryCustom.getHirePostListToSearch(
            null, null, codingTestY, null, createPageable(0, 0));
        List<HirePost> noCodingTestPostList = hirePostRepositoryCustom.getHirePostListToSearch(
            null, null, codingTestN, null, createPageable(0, 0));

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
        List<HirePost> assignmentPostList = hirePostRepositoryCustom.getHirePostListToSearch(
            null, null, null, assignmentY, createPageable(0, 0));
        List<HirePost> noAssignmentPostList = hirePostRepositoryCustom.getHirePostListToSearch(
            null, null, null, assignmentN, createPageable(0, 0));

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
        List<HirePost> postList = hirePostRepositoryCustom.getHirePostListToSearch(null,
            jobCategory, null, null, createPageable(0, 0));

        // Then
        assertThat(postList.size()).isEqualTo(3);
        for (HirePost post : postList) {
            assertThat(Hibernate.isInitialized(post.getCompany())).isTrue();
        }
    }


    @Test
    @DisplayName("최대 갯수를 초과한 페이지 조회")
    public void testSearchOverPage() {
        // Given
        List<String> jobCategory = new ArrayList<>();
        Integer page = 1;
        Integer pageSize = 3;

        // When
        List<HirePost> postList = hirePostRepositoryCustom.getHirePostListToSearch(null,
            jobCategory, null, null, createPageable(page, pageSize));

        // Then
        assertThat(postList.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("최대 채용 공고 갯수 이내의 페이지 조회")
    public void testSearchPage() {
        // Given
        List<String> jobCategory = new ArrayList<>();
        Integer page = 0;
        Integer pageSize = 3;

        // When
        List<HirePost> postList = hirePostRepositoryCustom.getHirePostListToSearch(null,
            jobCategory, null, null, createPageable(page, pageSize));

        // Then
        assertThat(postList.size()).isEqualTo(3);
        for (HirePost post : postList) {
            assertThat(Hibernate.isInitialized(post.getCompany())).isTrue();
        }
    }

    @Test
    @DisplayName("최대 채용 공고 갯수 이내의 페이지 조회")
    public void testSearchSecondPage() {
        // Given
        List<String> jobCategory = new ArrayList<>();
        Integer page = 1;
        Integer pageSize = 2;

        // When
        List<HirePost> postList = hirePostRepositoryCustom.getHirePostListToSearch(null,
            jobCategory, null, null, createPageable(page, pageSize));

        // Then
        assertThat(postList.size()).isEqualTo(1);
        assertThat(postList.get(0).getPostId()).isEqualTo(3);
        for (HirePost post : postList) {
            assertThat(Hibernate.isInitialized(post.getCompany())).isTrue();
        }
    }
}