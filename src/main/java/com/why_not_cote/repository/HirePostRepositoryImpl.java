package com.why_not_cote.repository;

import static com.why_not_cote.entity.post.QPostSkill.postSkill;
import static com.why_not_cote.entity.post.QSkill.skill;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.why_not_cote.entity.post.HirePost;
import com.why_not_cote.entity.post.QHirePost;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HirePostRepositoryImpl implements HirePostRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<HirePost> getHirePostListBySkillName(List<String> skillNameList) {
        QHirePost hirePost = QHirePost.hirePost;


        return queryFactory.selectFrom(hirePost)
            .innerJoin(postSkill).on(postSkill.hirePost.eq(hirePost))
            .innerJoin(skill).on(postSkill.skill.eq(skill))
            .where(skill.title.in(skillNameList))
            .fetch();


    }
}
