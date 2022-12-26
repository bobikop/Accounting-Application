package com.thegogetters.accounting.dto;

import com.thegogetters.accounting.enums.CompanyStatus;
import lombok.*;

import java.security.PrivateKey;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CompanyDto {

    private Long id;
    private String title;
    private String phone;
    private String website;
    private AddressDto address;
    private CompanyStatus companyStatus;


}
