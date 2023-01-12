package com.thegogetters.accounting.dto;

import lombok.*;
import javax.validation.constraints.*;


@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDTO {

    private Long id;

    @NotBlank (message = "Email is required field.")
    @Email(message = "A user with this email already exists. Please try with different email.")
    private String username;

    @NotBlank (message = "Password is required field.")
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,}", message = "Password should be at least 4 characters long and contains 1 capital letter, 1 small letter, 1 special character or number.")
    private String password;
    @NotNull(message = "Password should match")
    private String confirmPassword;

    @NotBlank(message = "First Name is required field.")
    @Size(max = 50, min = 2, message = "First Name must be between 2 and 50 characters long.")
    private String firstname;
    @NotBlank(message = "Last Name is required field.")
    @Size(max = 50, min = 2, message = "Last Name must be between 2 and 50 characters long.")
    private String lastname;

    @NotBlank (message = "Phone number is required field.")
//    @Pattern(regexp = "^\\d{10}$", message = "Phone Number is required field and may be in any valid phone number format.")
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$", message = "Phone Number is required field and may be in any valid phone number format.")
    private String phone;
    @NotNull(message = "Please select a role.")
    private RoleDTO role;

    @NotNull(message = "Please select a customer.")
    private CompanyDto company;

    private boolean isOnlyAdmin;

    public void setOnlyAdmin(boolean onlyAdmin) {
        isOnlyAdmin = onlyAdmin;
    }
}
