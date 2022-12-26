package com.thegogetters.accounting.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "companies")
public class Company extends BaseEntity{

    @Column(unique = true)
    private String title;

    private String phone;

    private String website;

//    private CompanyStatus companyStatus;

//    @OneToOne
//    private Address address;


}
