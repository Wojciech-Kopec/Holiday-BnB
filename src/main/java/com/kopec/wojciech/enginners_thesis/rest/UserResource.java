package com.kopec.wojciech.enginners_thesis.rest;

import com.kopec.wojciech.enginners_thesis.dto.UserDto;
import com.kopec.wojciech.enginners_thesis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserResource {
    private UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

//    @PostMapping()
//    public ResponseEntity<UserDto> save(@RequestBody UserDto user) {
//        if (user.getId() != null)
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Zapisywany obiekt nie może mieć ustawionego id");
//        UserDto savedUser = userService.save(user);
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(savedUser.getId())
//                .toUri();
//        return ResponseEntity.created(location).body(savedUser);
//    }
//
//    @GetMapping("")
//    public List<UserDto> findAll(@RequestParam(required = false) String username) {
//        if (username != null)
//            return userService.findByUsername(username);
//        else
//            return userService.findAll();
//    }
}
