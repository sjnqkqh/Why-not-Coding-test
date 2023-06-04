package com.why_not_cote.controller;

import com.why_not_cote.dto.hirePost.resp.SearchHirePostRespDto;
import com.why_not_cote.entity.post.HirePost;
import com.why_not_cote.service.HirePostService;
import com.why_not_cote.service.PostSkillService;
import com.why_not_cote.util.code.YnCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HirePostController {

    private final PostSkillService postSkillService;
    private final HirePostService hirePostService;

    @GetMapping("/api/hire-post/search")
    public List<SearchHirePostRespDto> searchHirePost(
        @RequestParam(value = "techStack", required = false) List<String> skillTitleList,
        @RequestParam(value = "jobCategory", required = false) List<String> jobCategoryList,
        @RequestParam(required = false) YnCode codingTestYn,
        @RequestParam(required = false) YnCode assignmentYn) {

        List<HirePost> postListByTechStacks
            = postSkillService.getPostSkillListByTitleList(skillTitleList);

        return hirePostService.searchHirePost(postListByTechStacks, jobCategoryList, codingTestYn,
            assignmentYn);
    }
}
