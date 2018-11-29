package com.kopec.wojciech.enginners_thesis.model;


import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.IndexColumn;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString(exclude = {"bookings", "accommodations"})
//exclusions needed to solve StackOverFlowException with bilateral-referencing

@Entity
@Table(name = "USERS"/*,
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"username", "email"})}*/)
//todo check if this can be removed when @Column(unique=true) is introduced


public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;
    @Column(unique = true)
    private String username;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(unique = true)
    @Email
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
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


    public void addBooking(Booking booking) {
        booking.setUser(this);
        getBookings().add(booking);
    }

    public void addAccommodation(Accommodation accommodation) {
        accommodation.setUser(this);
        getAccommodations().add(accommodation);
    }
}