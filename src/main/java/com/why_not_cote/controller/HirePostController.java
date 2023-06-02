package com.why_not_cote.controller;

import com.why_not_cote.dto.hirePost.req.SearchHirePostReqDto;
import com.why_not_cote.dto.hirePost.resp.SearchHirePostRespDto;
import com.why_not_cote.entity.post.HirePost;
import com.why_not_cote.service.HirePostService;
import com.why_not_cote.service.PostSkillService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HirePostController {

    private final PostSkillService postSkillService;
    private final HirePostService hirePostService;

    public List<SearchHirePostRespDto> searchHirePost(@RequestParam SearchHirePostReqDto reqDto) {
        List<HirePost> hirePostList =
            postSkillService.getPostSkillListByTitleList(reqDto.getSkillTitleList());

        return hirePostService.searchHirePost(hirePostList, reqDto.getJobCategoryList(),
            reqDto.getCodingTestYn(), reqDto.getAssignmentYn());
    }


}
