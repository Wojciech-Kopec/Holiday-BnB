package com.kopec.wojciech.enginners_thesis.model;


import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString

@Entity
@Table(name = "USERS")

public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;
    private String username;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
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
    @OrderColumn(name = "booking_id")
    private List<Booking> bookings = new ArrayList<>();

    @Fetch(value = FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @OrderColumn(name = "accommodation_id")
    private List<Accommodation> accommodations = new ArrayList<>();


    public void addBooking(Booking booking) {
        booking.setUser(this);
        getBookings().add(booking);
    }

    public void addAccommodation(Accommodation accommodation) {
        accommodation.setUser(this);
        getAccommodations().add(accommodation);
    }
}