package com.why_not_cote.repository;

import static com.why_not_cote.entity.post.QSkill.skill;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.why_not_cote.entity.post.HirePost;
import com.why_not_cote.entity.post.QHirePost;
import com.why_not_cote.entity.post.QPostSkill;
import com.why_not_cote.util.code.YnCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

@Repository
@RequiredArgsConstructor
public class HirePostRepositoryImpl implements HirePostRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    QHirePost hirePost = QHirePost.hirePost;
    QPostSkill postSkill = QPostSkill.postSkill;

    @Override
    public List<HirePost> getHirePostListBySkillName(
        List<Long> postIdList,
        List<String> jobCategoryList,
        YnCode codingTestYn,
        YnCode assignmentYn) {

        return queryFactory.selectFrom(hirePost).distinct()
            .innerJoin(postSkill).on(postSkill.hirePost.eq(hirePost))
            .innerJoin(skill).on(postSkill.skill.eq(skill))
            .where(
                jobCategoryIn(jobCategoryList),
                codingTestYnEq(codingTestYn),
                assignmentYnEq(assignmentYn)
            )
            .orderBy(hirePost.postId.asc())
            .fetch();
    }

    private BooleanExpression skillTitleIn(List<String> titleList) {
        if (ObjectUtils.isEmpty(titleList)) {
            return null;
        }

        return postSkill.skill.title.in(titleList);
    }

    private BooleanExpression jobCategoryIn(List<String> jobCategoryList) {
        if (ObjectUtils.isEmpty(jobCategoryList)) {
            return null;
        }
        return hirePost.jobCategory.in(jobCategoryList);
    }

    private BooleanExpression codingTestYnEq(YnCode codingTestYn) {
        if (codingTestYn == null) {
            return null;
        }
        return hirePost.codingTestYn.eq(codingTestYn);
    }

    private BooleanExpression assignmentYnEq(YnCode assignmentYn) {
        if (assignmentYn == null) {
            return null;
        }
        return hirePost.assignmentYn.eq(assignmentYn);
    }
}
