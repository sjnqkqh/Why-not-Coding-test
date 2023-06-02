package com.why_not_cote.dto.hirePost.resp;

import com.why_not_cote.entity.post.HirePost;
import com.why_not_cote.entity.post.Skill;
import com.why_not_cote.util.code.YnCode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.ObjectUtils;

@AllArgsConstructor
@Builder
@Getter
public class SearchHirePostRespDto {

    private Long postId;
    private String title;
    private String jobCategory;
    private Long companyId;
    private String companyName;

    private int minCareer;

    private int maxCareer;
    private List<String> techStacks;

    private YnCode codingTestYn;
    private YnCode assignmentYn;

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

    private List<String> convertTechStacks(List<Skill> skillList) {
        if (ObjectUtils.isEmpty(skillList)) {
            return new ArrayList<>();
        }
        return skillList.stream().map(Skill::getTitle).collect(Collectors.toList());
    }

    private int convertCareerStringToInt(String career) {
        if (career == null || career.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(career);
    }

}
