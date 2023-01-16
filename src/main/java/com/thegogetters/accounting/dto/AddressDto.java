package com.thegogetters.accounting.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {

    private Long id;

    @NotBlank
    @Size(max = 100,min = 2)
    private String addressLine1;

    @NotBlank
    @Size(max = 100)
    private String addressLine2;

    @NotBlank
    @Size(max = 50,min = 2)
    private String city;

    @NotBlank
    @Size(min = 2,max = 50)
    private String state;

    @NotBlank(message = "Country is required field.")
    @Size(min = 2, max = 50)
    private String country;

    @NotBlank
    @Pattern(regexp = "^\\d{5}([-]|\\s*)?(\\d{4})?$")
    private String zipCode;
}
