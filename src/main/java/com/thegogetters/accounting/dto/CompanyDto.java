package com.thegogetters.accounting.dto;

import com.thegogetters.accounting.enums.CompanyStatus;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.security.PrivateKey;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CompanyDto {

    private Long id;

    @NotBlank
    @Size(max = 100, min = 2)
    private String title;

    @NotBlank
    @Pattern(regexp = "^\\d{10}$")
    private String phone;

    @NotBlank
    @Size(max = 100,min = 2)
    @Pattern(regexp = "(https?:\\/\\/)?([\\w\\-])+\\.{1}([a-zA-Z]{2,63})([\\/\\w-]*)*\\/?\\??([^#\\n\\r]*)?#?([^\\n\\r]*)")
    private String website;

    @NotNull
    private AddressDto address;

    private CompanyStatus companyStatus;


}
