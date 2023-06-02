package com.why_not_cote.repository;


import static org.assertj.core.api.Assertions.assertThat;

import com.why_not_cote.config.DataIsolateTest;
import com.why_not_cote.entity.post.PostSkill;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


@DataIsolateTest
class PostSkillRepositoryTest {


    @Autowired
    private PostSkillRepository postSkillRepository;


    @Test
    @DisplayName("기술명에 따른 공고-스킬 매핑 데이터 조회")
    @Transactional(readOnly = true)
    public void testFindPostSkillListBySkillTitleList() {
        // Given
        List<String> titleList = List.of("Java");

        // When
        List<PostSkill> result = postSkillRepository.findPostSkillBySkill_TitleInOrderByPostSkillId(titleList);

        // Then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("존재하지 않는 기술명으로 공고-스킬 매핑 데이터 조회")
    @Transactional(readOnly = true)
    public void testFindPostSkillListByUnsavedSkillTitleList() {
        // Given
        List<String> titleList = List.of("Untitled");

        // When
        List<PostSkill> result = postSkillRepository.findPostSkillBySkill_TitleInOrderByPostSkillId(titleList);

        // Then
        assertThat(result.size()).isEqualTo(0);
    }



}