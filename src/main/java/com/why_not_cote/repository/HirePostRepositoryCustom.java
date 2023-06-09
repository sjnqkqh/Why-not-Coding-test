package com.why_not_cote.repository;

import com.why_not_cote.entity.post.HirePost;
import com.why_not_cote.util.code.YnCode;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface HirePostRepositoryCustom {
    List<HirePost> getHirePostListToSearch(
        List<HirePost> hirePostList,
        List<String> jobCategoryList,
        YnCode codingTestYn,
        YnCode assignmentYn, Pageable pageable);
}
