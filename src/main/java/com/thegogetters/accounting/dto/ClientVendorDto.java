package com.thegogetters.accounting.dto;

import com.thegogetters.accounting.enums.ClientVendorType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientVendorDto {

    private Long id;
    private String clientVendorName;
    private String phone;
    private String website;
    private ClientVendorType clientVendorType;
    private AddressDto addressDto;
//    private CompanyDto companyDto;
}
