package com.why_not_cote.entity.post;

import com.why_not_cote.entity.CommonBaseDateTime;

import javax.persistence.*;

@Entity
@Table(name = "TB_SKILL")
public class Skill extends CommonBaseDateTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private Long skillId;

    @Column(name = "title", unique = true)
    private String title;

}
