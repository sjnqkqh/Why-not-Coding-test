package com.why_not_cote.entity.post;

import com.why_not_cote.entity.CommonBaseDateTime;
import com.why_not_cote.entity.company.Company;
import com.why_not_cote.util.code.YnCode;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@Table(name = "TB_HIRING_POST")
public class HirePost extends CommonBaseDateTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "origin_post_id")
    private Long originPostId;

    @Column(name = "job_category")
    private String jobCategory;

    @Column(name = "post_title", nullable = false)
    private String postTitle;

    @Column(name = "content")
    private String content;

    @Column(name = "recruitment_process")
    private String recruitmentProcess;

    @BatchSize(size = 30)
    @OneToMany(targetEntity = Skill.class)
    @JoinTable(name = "TB_POST_SKILL",
            joinColumns = @JoinColumn(name = "POST_ID"),
            inverseJoinColumns = @JoinColumn(name = "SKILL_ID"))
    private List<Skill> skillList;

    @Column(name = "min_career")
    private String minCareer;

    @Column(name = "max_career")
    private String maxCareer;

    @Column(name = "education")
    private String education;

    @Column(name = "employment_type")
    private String employmentType;

    @Column(name = "working_region")
    private String workingRegion;

    @Column(name = "coding_test_exist_yn")
    @Enumerated(EnumType.STRING)
    private YnCode codingTestYn;

    @Column(name = "assignment_exist_yn")
    @Enumerated(EnumType.STRING)
    private YnCode assignmentYn;

    @Column(name = "only_image_yn")
    @Enumerated(EnumType.STRING)
    private YnCode onlyImageYn;

    @Column(name = "admin_checked_yn")
    @Enumerated(EnumType.STRING)
    private YnCode adminCheckedYn;

    @Column(name = "post_url")
    private String postUrl;

    @Override
    public String toString() {
        return "HiringPost{" +
            "postId=" + postId +
            ", originPostId=" + originPostId +
            ", jobCategory='" + jobCategory + '\'' +
            ", postTitle='" + postTitle + '\'' +
            ", content='" + content + '\'' +
            ", codingTestYn=" + codingTestYn +
            ", assignmentYn=" + assignmentYn +
            ", onlyImageYn=" + onlyImageYn +
            ", adminCheckedYn=" + adminCheckedYn +
            ", postUrl='" + postUrl + '\'' +
            '}';
    }
}
