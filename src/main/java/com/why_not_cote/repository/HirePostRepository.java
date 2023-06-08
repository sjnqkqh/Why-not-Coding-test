package com.why_not_cote.repository;

import com.why_not_cote.entity.post.HirePost;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HirePostRepository extends JpaRepository<HirePost, Long> {

    @EntityGraph(attributePaths = {"company"}, type = EntityGraphType.LOAD)
    Optional<HirePost> findFirstByPostId(Long postId);

}
