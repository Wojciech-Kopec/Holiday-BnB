package com.kopec.wojciech.enginners_thesis.service;

import com.kopec.wojciech.enginners_thesis.dto.AccommodationDto;
import com.kopec.wojciech.enginners_thesis.dto.BookingDto;
import com.kopec.wojciech.enginners_thesis.dto.UserDto;
import com.kopec.wojciech.enginners_thesis.exception.UserAlreadyExistException;
import com.kopec.wojciech.enginners_thesis.model.Accommodation;
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

    public UserDto save(UserDto userDto) {
        if (emailExist(userDto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: " + userDto.getEmail());
        }
        if (usernameExist(userDto.getUsername())) {
            throw new UserAlreadyExistException("There is an account with that username: " + userDto.getUsername());
        }

        return mapSavedUser(userDto);
    }

    private UserDto mapSavedUser(UserDto userDto) {
        User user = UserDto.toEntity(userDto);
        User savedUser = userRepository.save(user);
        return UserDto.toDto(savedUser);
    }

    public UserDto update(UserDto userDto) {
        return mapSavedUser(userDto);
    }

    public void delete(final UserDto userDto) {
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

    public List<UserDto> findByUsernameContaining(String username) {
        return userRepository.findAllByUsernameContainingIgnoreCaseOrderByUsernameAsc(username)
                .stream()
                .map(UserDto::toDto)
                .collect(Collectors.toList());
    }

    public UserDto findById(Integer id) {
        return UserDto.toDto(userRepository.findById(id).orElse(null));
    }

//    public UserDto findByAccommodation(AccommodationDto accommodation) {
//        return UserDto.toDto(userRepository.findByAccommodationsContains(AccommodationDto.toEntity(accommodation)));
//    }
//
//    public UserDto findByBooking(BookingDto booking) {
//        return UserDto.toDto(userRepository.findByBookingsContains(BookingDto.toEntity(booking)));
//    }
}
