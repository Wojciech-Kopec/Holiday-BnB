package com.kopec.wojciech.enginners_thesis.model;


import com.kopec.wojciech.enginners_thesis.validation.ExtendedEmailValidator;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.IndexColumn;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(exclude = {"id", "password", "bookings", "accommodations"})
//exclusions for lists needed to solve StackOverFlowException with bilateral-referencing
@Builder()

@Entity
@Table(name = "USERS",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"username", "email"})})

public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;


    @NotNull
    @Size(min = 5, max = 50)
    private String username;

    @Column(name = "first_name")
    @NotNull
    @Size(min = 5, max = 50)
    private String firstName;

    @Column(name = "last_name")
    @NotNull
    @Size(min = 5, max = 50)
    private String lastName;

    @NotNull
    @ExtendedEmailValidator
    private String email;

    @Column(name = "phone_number")
    @NotNull
    @Pattern(regexp = "^[0-9]*$")
    @Size(min = 7, max = 12)
    private String phoneNumber;

    @NotNull
    @Size(min = 5, max = 100)
    private String password;


    @Fetch(value = FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
//    @OrderColumn(name = "booking_id")
    @IndexColumn(name = "booking_id", base = 1)
    private List<Booking> bookings;

    @Fetch(value = FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
//    @OrderColumn(name = "accommodation_id")
    @IndexColumn(name = "accommodation_id", base = 1)
    private List<Accommodation> accommodations;
}