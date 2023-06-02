package com.why_not_cote.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.why_not_cote.config.DataIsolateTest;
import com.why_not_cote.entity.post.HirePost;
import java.util.List;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DataIsolateTest
class PostSkillServiceTest {

    @Autowired
    private PostSkillService postSkillService;

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