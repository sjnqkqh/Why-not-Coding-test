package com.why_not_cote.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.why_not_cote.entity.post.HirePost;
import com.why_not_cote.entity.post.QHirePost;
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

    @Override
    public List<HirePost> getHirePostListBySkillName(
        List<HirePost> postList,
        List<String> jobCategoryList,
        YnCode codingTestYn,
        YnCode assignmentYn) {

        return queryFactory.selectFrom(hirePost)
            .where(
                hirePostIn(postList),
                jobCategoryIn(jobCategoryList),
                codingTestYnEq(codingTestYn),
                assignmentYnEq(assignmentYn)
            )
            .orderBy(hirePost.postId.asc())
            .fetch();
    }

    private BooleanExpression hirePostIn(List<HirePost> postList) {
        if (ObjectUtils.isEmpty(postList)) {
            return null;
        }

        return  hirePost.in(postList);
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