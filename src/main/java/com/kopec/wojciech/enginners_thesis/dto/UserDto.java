package com.kopec.wojciech.enginners_thesis.dto;

import com.kopec.wojciech.enginners_thesis.model.User;
import lombok.Data;

@Data
public class UserDto implements DtoConvertible<User, UserDto> {
    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;


    @Override
    public UserDto toDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User toEntity() {
        return modelMapper.map(this, User.class);
    }
}
