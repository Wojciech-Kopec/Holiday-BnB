package com.kopec.wojciech.enginners_thesis.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "bookings")
@ToString(exclude = "bookings")

@Entity
@Table(name = "ACCOMMODATIONS")

public class Accommodation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accommodation_id")
    private long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Localization localization;
    @Column(name = "accommodation_type")
    @NotBlank
    private String accommodationType;
    @OneToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinColumn(name = "accommodation_id")
    private List<Amenity> amenities;
    @Column(name = "max_guests")
    @Positive
    private int maxGuests;
    private LocalDateTime createdDate;
    @Column(name = "price_per_night")
    @Positive
    private int pricePerNight;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //TODO to analyze
    @OneToMany(mappedBy = "accommodation",
            orphanRemoval = true)
    private List<Booking> bookings;

}
