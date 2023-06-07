package com.why_not_cote.dto.hirePost.resp;

import com.why_not_cote.entity.post.Skill;
import com.why_not_cote.util.code.YnCode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.util.ObjectUtils;

@Getter
public class HirePostDto {

    protected Long postId;
    protected String title;
    protected String jobCategory;
    protected Long companyId;
    protected String companyName;

    protected int minCareer;

    protected int maxCareer;
    protected List<String> techStacks;

    protected YnCode codingTestYn;
    protected YnCode assignmentYn;

    protected List<String> convertTechStacks(List<Skill> skillList) {
        if (ObjectUtils.isEmpty(skillList)) {
            return new ArrayList<>();
        }
        return skillList.stream().map(Skill::getTitle).collect(Collectors.toList());
    }

    protected int convertCareerStringToInt(String career) {
        if (career == null || career.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(career);
    }
}
