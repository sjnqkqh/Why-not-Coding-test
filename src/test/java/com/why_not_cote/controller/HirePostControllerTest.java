package com.why_not_cote.controller;



import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.why_not_cote.config.DataIsolateTest;
import com.why_not_cote.config.EnableMockMvcUTF8;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@DataIsolateTest
@EnableMockMvcUTF8
class HirePostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testEmptyParamSearch() throws Exception {
        // Given
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // When
        ResultActions result = mockMvc.perform(RestDocumentationRequestBuilders.get("/api/hire-post/search")
            .headers(headers));

        // Then
        System.out.println("result = " + result);
        result.andExpect(status().isOk());
    }


}