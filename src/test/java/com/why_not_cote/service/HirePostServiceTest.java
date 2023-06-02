package com.why_not_cote.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.why_not_cote.config.DataIsolateTest;
import com.why_not_cote.dto.hirePost.resp.SearchHirePostRespDto;
import com.why_not_cote.entity.post.HirePost;
import com.why_not_cote.util.code.YnCode;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

@DataIsolateTest
class HirePostServiceTest {

    @Autowired
    private HirePostService hirePostService;

    @Autowired
    private PostSkillService postSkillService;


    @Test
    @DisplayName("검색 조건이 하나도 없이 채용 공고 조회")
    public void testSearchWithoutAnyKeyword() {
        // Given - Nothing

        // When
        List<SearchHirePostRespDto> result = hirePostService.searchHirePost(null, null, null, null);

        // Then
        assertThat(result.size()).isEqualTo(3);
    }


    @Test
    @DisplayName("기술명에 따른 채용 공고 조회")
    public void testSearchBySkillTitle() {
        // Given
        List<String> titleList = List.of("Java");

        // When
        List<HirePost> postSkillList = postSkillService.getPostSkillListByTitleList(titleList);
        List<SearchHirePostRespDto> result = hirePostService.searchHirePost(postSkillList, null, null, null);

        // Then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getPostId()).isEqualTo(1);
        assertThat(result.get(1).getPostId()).isEqualTo(2);
    }

    @Test
    @DisplayName("존재하지 않는 기술명으로 채용 공고 검색")
    public void testSearchWithUnsavedSkillTitle() {
        // Given
        List<String> titleList = List.of("Untitled");
        List<HirePost> postSkillList = postSkillService.getPostSkillListByTitleList(titleList);

        // When
        List<SearchHirePostRespDto> postList = hirePostService.searchHirePost(postSkillList, null, null, null);

        // Then
        assertThat(postList.size()).isEqualTo(0);
    }

    @DisplayName("기술명+코테 유무로 검색")
    @ParameterizedTest
    @EnumSource(value = YnCode.class)
    public void testSearchWithSkillAndCodingTestYn(YnCode codingTestYn) {
        // Given
        List<String> titleList = List.of("Java");
        List<HirePost> postSkillList = postSkillService.getPostSkillListByTitleList(titleList);

        // When
        List<SearchHirePostRespDto> postList = hirePostService.searchHirePost(postSkillList, null, codingTestYn,
            null);

        // Then
        if (codingTestYn == YnCode.Y) {
            assertThat(postList.size()).isEqualTo(1);
            assertThat(postList.get(0).getPostId()).isEqualTo(2);
        } else {
            assertThat(postList.size()).isEqualTo(1);
            assertThat(postList.get(0).getPostId()).isEqualTo(1);
        }
    }

    @DisplayName("과제물 전형 유무로 채용공고 검색")
    @ParameterizedTest
    @EnumSource(YnCode.class)
    public void testSearchWithAssignmentYn(YnCode assignmentYn) {
        // Given - assignmentYn

        // When
        List<SearchHirePostRespDto> postList = hirePostService.searchHirePost(null, null,
            null, assignmentYn);

        // Then
        if (assignmentYn == YnCode.Y) {
            assertThat(postList.size()).isEqualTo(1);
            assertThat(postList.get(0).getPostId()).isEqualTo(3);
        }
    }

    @DisplayName("직무 카테고리 채용 공고 검색")
    @ParameterizedTest
    @ValueSource(strings = {"웹 풀스택 개발자", "UnsavedCategory"})
    public void testSearchWithSkillAndJobCategory(String jobCategory) {
        // Given
        List<String> jobCategoryList = List.of(jobCategory);

        // When
        List<SearchHirePostRespDto> postList = hirePostService.searchHirePost(null, jobCategoryList,
            null, null);

        // Then
        if (jobCategory.equals("웹 풀스택 개발자")) {
            assertThat(postList.size()).isEqualTo(1);
            assertThat(postList.get(0).getPostId()).isEqualTo(3);
        } else {
            assertThat(postList.size()).isEqualTo(0);
        }
    }
}