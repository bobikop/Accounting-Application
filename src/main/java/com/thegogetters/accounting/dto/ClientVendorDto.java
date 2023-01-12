package com.thegogetters.accounting.dto;

import com.thegogetters.accounting.enums.ClientVendorType;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientVendorDto {

    private Long id;

    @NotBlank
    @Size(min = 2, max = 50)
    private String clientVendorName;

    @NotBlank
    private String phone;

   @Pattern(regexp = "^http(s{0,1})://[a-zA-Z0-9/\\-\\.]+.([A-Za-z/]{2,5})[a-zA-Z0-9/\\&\\?\\=\\-\\.\\~\\%]*",
            message = "Website should have a valid format.")
    private String website;

    @NotNull
    private ClientVendorType clientVendorType;

    @Valid
    private AddressDto address;

    @Valid
    private CompanyDto company;
}
