package com.app.utils.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@ToString
public class UserCreationDto implements Serializable {
    @Size(min = 3, max = 20, message = "Your name should be between 3 and 20 characters")
    @Pattern(regexp = "[A-Za-z]+")
    private final String firstName;
    @Size(min = 3, max =40, message = "Your name should be between 3 and 40 characters")
    @Pattern(regexp = "[A-Za-z]+")
    private final String lastName;
    @Size(min = 3, max = 10, message = "Your code name should be between 3 and 10 characters")
    @Pattern(regexp = "[A-Za-z1-9]+")
    private final String codeName;
    @Size(min = 8, max = 20, message = "Your password should be between 8 and 20 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[.-_])(?=\\S+$).{8,}$")
    private final String password;
}
