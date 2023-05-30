package com.why_not_cote.repository;

import com.why_not_cote.entity.post.HirePost;
import java.util.List;

public interface HirePostRepositoryCustom {
    List<HirePost> getHirePostListBySkillName(List<String> skillNameList);
}
