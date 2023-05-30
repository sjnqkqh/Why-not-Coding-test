package com.why_not_cote.service;

import com.why_not_cote.entity.post.PostSkill;
import com.why_not_cote.repository.PostSkillRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class PostSkillService {
    private PostSkillRepository postSkillRepository;

    public List<PostSkill> getPostSkillListByTitleList(List<String> titleList){

        return null;
    }



}
