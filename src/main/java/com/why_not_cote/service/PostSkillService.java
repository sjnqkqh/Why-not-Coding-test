package com.why_not_cote.service;

import com.why_not_cote.entity.post.HirePost;
import com.why_not_cote.entity.post.PostSkill;
import com.why_not_cote.repository.PostSkillRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostSkillService {

    private final PostSkillRepository postSkillRepository;

    /**
     * 기술명을 기반으로 해당되는 공고 목록을 반환한다.
     * 단, 해당 HirePost 객체는 프록시 상태로 반환한다.
     *
     * @param titleList 기술명 목록
     * @return HirePost Proxy 목록
     */
    public List<HirePost> getPostSkillListByTitleList(List<String> titleList) {
        return postSkillRepository.findPostSkillBySkill_TitleInOrderByPostSkillId(titleList)
            .stream().map(PostSkill::getHirePost).collect(Collectors.toList());
    }


}
