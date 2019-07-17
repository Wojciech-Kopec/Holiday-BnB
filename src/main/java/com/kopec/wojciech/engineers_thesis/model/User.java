package com.kopec.wojciech.engineers_thesis.model;


import com.kopec.wojciech.engineers_thesis.validation.ExtendedEmailValidator;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"roles", "password"}, callSuper = false)
@ToString(exclude = {"password"}, callSuper = true)
@Builder

@Entity
@Table(name = "USERS",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"username", "email"})})

public class User extends AbstractEntity {

    @NotNull
    @Size(min = 5, max = 50)
    private String username;

    @Column(name = "first_name")
    @NotNull
    @Size(min = 3, max = 50)
    private String firstName;

    @Column(name = "last_name")
    @NotNull
    @Size(min = 3, max = 50)
    private String lastName;

    @NotNull
    @ExtendedEmailValidator
    private String email;

    @Column(name = "phone_number")
    @NotNull
    @Pattern(regexp = "^[0-9]*$")
    @Size(min = 7, max = 14)
    private String phoneNumber;

    @NotNull
    @Size(min = 5, max = 100)
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
    @Singular
    private Set<Role> roles;

}