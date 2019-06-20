package com.kopec.wojciech.enginners_thesis.service;

import com.kopec.wojciech.enginners_thesis.dto.UserDto;
import com.kopec.wojciech.enginners_thesis.exception.UserAlreadyExistException;
import com.kopec.wojciech.enginners_thesis.model.User;
import com.kopec.wojciech.enginners_thesis.repository.AccommodationRepository;
import com.kopec.wojciech.enginners_thesis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AccommodationRepository accommodationRepository;

    private PasswordEncoder encoder;
    @Autowired
    public UserService(UserRepository userRepository,
                       AccommodationRepository accommodationRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.accommodationRepository = accommodationRepository;
        this.encoder = encoder;
    }

    public UserDto save(UserDto userDto) {
        if (emailExist(userDto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: " + userDto.getEmail());
        }
        if (usernameExist(userDto.getUsername())) {
            throw new UserAlreadyExistException("There is an account with that username: " + userDto.getUsername());
        }
        userDto.setPassword(encoder.encode(userDto.getPassword()));
        return mapSavedUser(userDto);
    }

    private UserDto mapSavedUser(UserDto userDto) {
        User user = UserDto.toEntity(userDto);
        User savedUser = userRepository.save(user);
        return UserDto.toDto(savedUser);
    }

    public UserDto update(UserDto userDto) {
        UserDto preUpdatedUser = this.findById(userDto.getId());
        if(!preUpdatedUser.getPassword().equals(userDto.getPassword())) {
            //password has been changed
            userDto.setPassword(encoder.encode(userDto.getPassword()));
        }
        return mapSavedUser(userDto);
    }

    public void delete(final UserDto userDto) {
        User user = UserDto.toEntity(userDto);
        //TODO find a way to do it within JPA
        accommodationRepository.findAllByUserOrderByCreatedDateDesc(user)
                .forEach(accommodationRepository::delete);
        userRepository.delete(user);
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

    public List<UserDto> findByUsernameContaining(String username) {
        return userRepository.findAllByUsernameContainingIgnoreCaseOrderByUsernameAsc(username)
                .stream()
                .map(UserDto::toDto)
                .collect(Collectors.toList());
    }

    public UserDto findById(Integer id) {
        return UserDto.toDto(userRepository.findById(id).orElse(null));
    }
}
