package com.why_not_cote.service;

import com.why_not_cote.entity.post.HirePost;
import com.why_not_cote.repository.HirePostRepository;
import com.why_not_cote.repository.HirePostRepositoryCustom;
import com.why_not_cote.util.code.YnCode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HirePostService {

    private final HirePostRepository hirePostRepository;
    private final HirePostRepositoryCustom hirePostRepositoryCustom;

    public List<HirePost> searchHirePost(List<HirePost> postSkillList,
        List<String> jobCategoryList, YnCode codingTestYn, YnCode assignmentYn) {

        if (!Objects.isNull(postSkillList) && postSkillList.size() == 0) {
            return new ArrayList<>();
        }

        List<HirePost> result = hirePostRepositoryCustom.getHirePostListBySkillName(postSkillList,
            jobCategoryList, codingTestYn, assignmentYn);

        return result;
    }

}
