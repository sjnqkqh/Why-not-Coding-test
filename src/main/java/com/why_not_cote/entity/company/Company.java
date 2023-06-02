package com.why_not_cote.entity.company;

import com.why_not_cote.entity.CommonBaseDateTime;
import com.why_not_cote.entity.post.HirePost;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "TB_COMPANY")
@NoArgsConstructor
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

    @Builder
    public Company(String originCompanyId, String companyName,
        String industryType, String companyType, Long employeeCount, String address,
        String homepage,
        String phoneNumber, String thumbnailUrl) {
        this.originCompanyId = originCompanyId;
        this.companyName = companyName;
        this.industryType = industryType;
        this.companyType = companyType;
        this.employeeCount = employeeCount;
        this.address = address;
        this.homepage = homepage;
        this.phoneNumber = phoneNumber;
        this.thumbnailUrl = thumbnailUrl;
    }
}
