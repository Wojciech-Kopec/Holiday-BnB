package com.kopec.wojciech.enginners_thesis.model;


import com.kopec.wojciech.enginners_thesis.validation.ExtendedEmailValidator;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.IndexColumn;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"bookings", "accommodations", "roles", "password"}, callSuper = false)
@ToString(exclude = {"password", "bookings", "accommodations"}, callSuper = true)
//exclusions for lists needed to solve StackOverFlowException with bilateral-referencing
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
    @Size(min = 7, max = 14)
    private String phoneNumber;

    @NotNull
    @Size(min = 5, max = 100)
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
    @Singular
    private Set<Role> roles;

    @Fetch(value = FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "user",
            fetch = FetchType.LAZY,
//            cascade = CascadeType.ALL, COULD NOT DELETE OBJECTS WITH THIS
            orphanRemoval = true)
//    @OrderColumn(name = "booking_id")
    @IndexColumn(name = "id", base = 1)
    @Singular
    private List<Booking> bookings;

    @Fetch(value = FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "user",
            fetch = FetchType.LAZY,
//            cascade = CascadeType.ALL,
            orphanRemoval = true)
//    @OrderColumn(name = "accommodation_id")
    @IndexColumn(name = "id", base = 1)
    @Singular
    private List<Accommodation> accommodations;


    public void setAccommodations(List<Accommodation> accommodations) {
        this.accommodations.clear();
        if (accommodations != null) {
            this.accommodations.addAll(accommodations);
        }
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings.clear();
        this.bookings.addAll(bookings);
    }

}