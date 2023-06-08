package com.why_not_cote.dto.hirePost.resp;

import com.why_not_cote.entity.post.HirePost;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchHirePostRespDto extends HirePostDto{

    public SearchHirePostRespDto(HirePost hirePost) {
        this.postId = hirePost.getPostId();
        this.title = hirePost.getPostTitle();
        this.jobCategory = hirePost.getJobCategory();

        this.companyId = hirePost.getCompany().getId();
        this.companyName = hirePost.getCompany().getCompanyName();

        this.minCareer = convertCareerStringToInt(hirePost.getMinCareer());
        this.maxCareer = convertCareerStringToInt(hirePost.getMaxCareer());

        this.techStacks = convertTechStacks(hirePost.getSkillList());

        this.codingTestYn = hirePost.getCodingTestYn();
        this.assignmentYn = hirePost.getAssignmentYn();
    }



}
