package com.why_not_cote.controller;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.why_not_cote.config.DataIsolateTest;
import com.why_not_cote.config.EnableMockMvcUTF8;
import com.why_not_cote.dto.hirePost.resp.SearchHirePostRespDto;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@DataIsolateTest
@EnableMockMvcUTF8
class HirePostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("파라미터 없이 채용 공고 목록 검색")
    public void testSearchWithEmptyParam() throws Exception {
        // Given
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // When
        ResultActions result = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/hire-post/search")
                .headers(headers)
        );

        // Then
        MvcResult mvcResult = result.andExpect(status().isOk())
            .andDo(print())
            .andDo(document("searchHirePost",
                    requestParameters(
                        parameterWithName("techStack").description("기술 스택").optional(),
                        parameterWithName("jobCategory").description("직무 카테고리").optional(),
                        parameterWithName("codingTestYn").description("코딩 테스트 유무").optional(),
                        parameterWithName("assignmentYn").description("과제물 전형 유무").optional()),
                    responseFields(
                        fieldWithPath("[].postId").description("채용 공고 ID"),
                        fieldWithPath("[].title").description("채용 공고명"),
                        fieldWithPath("[].jobCategory").description("직무 유형"),
                        fieldWithPath("[].companyId").description("회사 ID"),
                        fieldWithPath("[].companyName").description("회사명"),
                        fieldWithPath("[].minCareer").description("최소 경력"),
                        fieldWithPath("[].maxCareer").description("최대 경력"),
                        fieldWithPath("[].techStacks").description("기술 스택"),
                        fieldWithPath("[].codingTestYn").description("코딩 테스트 유무"),
                        fieldWithPath("[].assignmentYn").description("과제물 전형 유무")
                    )
                )
            ).andReturn();

        List<SearchHirePostRespDto> dtoList = convertResponseToSearchResultDtoList(mvcResult);
        assertThat(dtoList.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("채용 공고 단건 조회")
    public void testGetHirePostDetail() throws Exception {
        // Given
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Long postId = 1L;

        // When
        ResultActions result = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/hire-post/{postId}", postId)
                .headers(headers)
        );

        // Then
        result.andExpect(status().isOk())
            .andDo(print())
            .andDo(document("getHirePostDetail",
                    pathParameters(
                        parameterWithName("postId").description("채용 공고 ID")
                    ),
                    responseFields(
                        fieldWithPath("postId").description("채용 공고 ID"),
                        fieldWithPath("title").description("채용 공고명"),
                        fieldWithPath("content").description("채용 상세내용"),
                        fieldWithPath("recruitProcess").description("채용 절차"),
                        fieldWithPath("jobCategory").description("직무 유형"),
                        fieldWithPath("companyId").description("회사 ID"),
                        fieldWithPath("companyName").description("회사명"),
                        fieldWithPath("minCareer").description("최소 경력"),
                        fieldWithPath("maxCareer").description("최대 경력"),
                        fieldWithPath("techStacks").description("기술 스택"),
                        fieldWithPath("codingTestYn").description("코딩 테스트 유무"),
                        fieldWithPath("assignmentYn").description("과제물 전형 유무")
                    )
                )
            );
    }

    @Test
    @DisplayName("기술 스택에 따른 채용 공고 목록 검색")
    public void testSearchWithTechStacks() throws Exception {
        // Given
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
        requestMap.put("techStack", List.of("Java", "Kotlin"));

        // When
        ResultActions result = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/hire-post/search")
                .headers(headers)
                .params(requestMap)

        );

        // Then
        MvcResult mvcResult = result.andExpect(status().isOk())
            .andDo(print())
            .andReturn();

        List<SearchHirePostRespDto> dtoList = convertResponseToSearchResultDtoList(mvcResult);
        assertThat(dtoList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("직무 유형에 따른 채용 공고 목록 검색")
    public void testSearchWithJobCategory() throws Exception {
        // Given
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String jobCategory = "웹 풀스택 개발자";

        // When
        ResultActions result = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/hire-post/search")
                .headers(headers)
                .param("jobCategory", jobCategory)
        );

        // Then
        MvcResult mvcResult = result.andExpect(status().isOk())
            .andDo(print())
            .andReturn();

        List<SearchHirePostRespDto> dtoList = convertResponseToSearchResultDtoList(mvcResult);
        assertThat(dtoList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("코딩테스트 유무에 따른 채용 공고 목록 검색")
    public void testSearchWithCodingTestYn() throws Exception {
        // Given
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String codingTestYn = "Y";

        // When
        ResultActions result = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/hire-post/search")
                .headers(headers)
                .param("codingTestYn", codingTestYn)
        );

        // Then
        MvcResult mvcResult = result.andExpect(status().isOk())
            .andDo(print())
            .andReturn();

        List<SearchHirePostRespDto> dtoList = convertResponseToSearchResultDtoList(mvcResult);
        assertThat(dtoList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("과제물 전형 유무에 따른 채용 공고 목록 검색")
    public void testSearchWithAssignmentYn() throws Exception {
        // Given
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String assignmentYn = "Y";

        // When
        ResultActions result = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/hire-post/search")
                .headers(headers)
                .param("assignmentYn", assignmentYn)
        );

        // Then
        MvcResult mvcResult = result.andExpect(status().isOk())
            .andDo(print())
            .andReturn();

        List<SearchHirePostRespDto> dtoList = convertResponseToSearchResultDtoList(mvcResult);
        assertThat(dtoList.size()).isEqualTo(1);
    }

    private List<SearchHirePostRespDto> convertResponseToSearchResultDtoList(MvcResult mvcResult)
        throws IOException {
        TypeReference<List<SearchHirePostRespDto>> responseType = new TypeReference<>() {
        };

        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), responseType);
    }
}