package com.why_not_cote.entity.company;

import com.why_not_cote.entity.CommonBaseDateTime;
import com.why_not_cote.entity.post.HirePost;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "TB_COMPANY")
public class Company extends CommonBaseDateTime {
    @Id
    @Column(name = "company_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "company")
    private List<HirePost> postList;

    @Column(name = "origin_company_id")
    private String originCompanyId;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "industry_type")
    private String industryType;

    @Column(name = "company_type")
    private String companyType;

    @Column(name = "employee_count")
    private Long employeeCount;

    @Column(name = "address")
    private String address;

    @Column(name = "homepage")
    private String homepage;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

}
