package com.kopec.wojciech.enginners_thesis.service;

import com.kopec.wojciech.enginners_thesis.dto.UserDto;
import com.kopec.wojciech.enginners_thesis.exception.UserAlreadyExistException;
import com.kopec.wojciech.enginners_thesis.model.User;
import com.kopec.wojciech.enginners_thesis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerNewUserAccount(final UserDto accountDto) {
        if (emailExist(accountDto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: " + accountDto.getEmail());
        }
        if(usernameExist(accountDto.getUsername())) {
            throw new UserAlreadyExistException("There is an account with that username: " + accountDto.getUsername());
        }

        final User user = UserDto.toEntity(accountDto);
        return userRepository.save(user);
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
}
