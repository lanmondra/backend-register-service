package com.register.service.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpDto {

    @NotEmpty
    private String name;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String email;
    
    @NotEmpty
    private String role;

    @NotEmpty
    private char[] password;

}
