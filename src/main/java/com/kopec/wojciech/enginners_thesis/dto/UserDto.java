package com.kopec.wojciech.enginners_thesis.dto;

import com.kopec.wojciech.enginners_thesis.model.User;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class UserDto {
    private Integer id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    private static ModelMapper modelMapper = new ModelMapper();

    public static UserDto toDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public static User toEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }
}
