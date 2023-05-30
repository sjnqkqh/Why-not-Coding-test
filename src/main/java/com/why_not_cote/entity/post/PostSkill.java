package com.why_not_cote.entity.post;

import com.why_not_cote.entity.CommonBaseDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "TB_POST_SKILL")
public class PostSkill extends CommonBaseDateTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_skill_id")
    private Long postSkillId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private HirePost hirePost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id")
    private Skill skill;

    public PostSkill(HirePost hirePost, Skill skill) {
        this.hirePost = hirePost;
        this.skill = skill;
    }

    @Override
    public String toString() {
        return "PostSkill{" +
            "postSkillId=" + postSkillId +
            ", hiringPost=" + hirePost +
            ", skill=" + skill +
            '}';
    }
}
