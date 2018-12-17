package com.kopec.wojciech.enginners_thesis.service;

import com.kopec.wojciech.enginners_thesis.dto.UserDto;
import com.kopec.wojciech.enginners_thesis.exception.UserAlreadyExistException;
import com.kopec.wojciech.enginners_thesis.model.User;
import com.kopec.wojciech.enginners_thesis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto registerNewUserAccount(final UserDto accountDto) {
        if (emailExist(accountDto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: " + accountDto.getEmail());
        }
        if (usernameExist(accountDto.getUsername())) {
            throw new UserAlreadyExistException("There is an account with that username: " + accountDto.getUsername());
        }

        final User user = UserDto.toEntity(accountDto);
        return UserDto.toDto(userRepository.save(user));
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(final UserDto userDto) {
        userRepository.delete(UserDto.toEntity(userDto));
    }

    private boolean emailExist(final String email) {
        return userRepository.findByEmail(email) != null;
    }

    private boolean usernameExist(final String username) {
        return userRepository.findByUsername(username) != null;
    }

    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(UserDto::toDto).collect(Collectors.toList());
    }

    public UserDto findByUsername(String username) {
        return UserDto.toDto(userRepository.findByUsername(username));
    }
}
