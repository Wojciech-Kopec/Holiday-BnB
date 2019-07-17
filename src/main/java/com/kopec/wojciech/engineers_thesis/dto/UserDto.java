package com.kopec.wojciech.engineers_thesis.dto;

import com.kopec.wojciech.engineers_thesis.model.User;
import com.kopec.wojciech.engineers_thesis.validation.ExtendedEmailValidator;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserDto {

    private Integer id;

    @NotNull
    @Size(min = 5, max = 50)
    private String username;

    @NotNull
    @Size(min = 3, max = 50)
    private String firstName;

    @NotNull
    @Size(min = 3, max = 50)
    private String lastName;

    @NotNull
    @ExtendedEmailValidator
    private String email;

    @NotNull
    @Pattern(regexp = "^[0-9]*$")
    @Size(min = 7, max = 14)
    private String phoneNumber;

    @NotNull
    @Size(min = 5, max = 100)
    private String password;

    private static ModelMapper modelMapper = new ModelMapper();

    public static UserDto toDto(User user) {
        return user != null ? modelMapper.map(user, UserDto.class) : null;
    }

    public static User toEntity(UserDto userDto) {
        return userDto != null ? modelMapper.map(userDto, User.class) : null;
    }
}
