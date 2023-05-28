package com.why_not_cote.entity.post;

import com.why_not_cote.entity.CommonBaseDateTime;

import javax.persistence.*;

@Entity
@Table(name = "TB_POST_SKILL")
public class PostSkill extends CommonBaseDateTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_skill_id")
    private Long postSkillId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private HiringPost hiringPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id")
    private Skill skill;

}
