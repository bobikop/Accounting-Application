package com.thegogetters.accounting.dto;

import com.thegogetters.accounting.enums.CompanyStatus;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.security.PrivateKey;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CompanyDto {

    private Long id;

    @NotBlank
    @Size(max = 100, min = 2)
    private String title;

    @NotBlank
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$" // +111 (202) 555-0125  +1 (202) 555-0125
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"                                  // +111 123 456 789
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$")
    private String phone;

    @NotBlank
    @Size(max = 100,min = 2)
    @Pattern(regexp = "^http(s{0,1})://[a-zA-Z0-9/\\-\\.]+\\.([A-Za-z/]{2,5})[a-zA-Z0-9/\\&\\?\\=\\-\\.\\~\\%]*")
    private String website;

    @Valid
    private AddressDto address;

    private CompanyStatus companyStatus;


}
