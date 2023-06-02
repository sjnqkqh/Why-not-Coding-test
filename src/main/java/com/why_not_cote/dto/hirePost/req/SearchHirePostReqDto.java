package com.why_not_cote.dto.hirePost.req;

import com.why_not_cote.util.code.YnCode;
import java.util.List;
import lombok.Getter;

@Getter
public class SearchHirePostReqDto {
    private List<String> skillTitleList;
    private List<String> jobCategoryList;
    private YnCode codingTestYn;
    private YnCode assignmentYn;
}
