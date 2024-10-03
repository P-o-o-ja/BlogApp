package com.pooja.blogApp.payloads;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter

public class UserDto {

    Integer id;

    @NotEmpty
    @Size(min=4, message = "Username must be minimum of 4 character!")
    String name;

    @Email(message = "Email address is not valid!")
    String email;

    @NotEmpty
    @Size(min=8, max=12, message = "Password must be minimum of 8 characters and maximum of 12 character!")
    String password;

    @NotEmpty
    String about;

    private Set<RoleDto> roles=new HashSet<>();
}
