package com.why_not_cote.service;

import com.why_not_cote.dto.hirePost.resp.SearchHirePostRespDto;
import com.why_not_cote.entity.post.HirePost;
import com.why_not_cote.repository.HirePostRepositoryCustom;
import com.why_not_cote.util.code.YnCode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HirePostService {

    private final HirePostRepositoryCustom hirePostRepositoryCustom;

    @Transactional(readOnly = true)
    public List<SearchHirePostRespDto> searchHirePost(List<HirePost> postSkillList,
        List<String> jobCategoryList, YnCode codingTestYn, YnCode assignmentYn) {

        if (!Objects.isNull(postSkillList) && postSkillList.size() == 0) {
            return new ArrayList<>();
        }

        List<HirePost> result = hirePostRepositoryCustom.getHirePostListBySkillName(postSkillList,
            jobCategoryList, codingTestYn, assignmentYn);

        return result.stream().map(SearchHirePostRespDto::new).collect(Collectors.toList());
    }

}
