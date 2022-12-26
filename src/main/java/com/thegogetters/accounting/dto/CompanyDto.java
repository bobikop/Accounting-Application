package com.thegogetters.accounting.dto;

import lombok.*;

import java.security.PrivateKey;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CompanyDto {

    private Long id;
    private String title;
    private String phone;
    private String website;
    private AddressDto address;
   // Private CompanyStatus companyStatus;


}
