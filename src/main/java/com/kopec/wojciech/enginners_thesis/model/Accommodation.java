package com.kopec.wojciech.enginners_thesis.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"id","bookings"})
@ToString(exclude = "bookings")
@Builder

@Entity
@Table(name = "ACCOMMODATIONS")

public class Accommodation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accommodation_id")
    private long id;

    @NotNull
    @Size(min = 10, max = 120)
    private String name;

    @NotNull
    @Size(min = 100)
    private String description;

    @Column(name = "accommodation_type")
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private AccommodationType accommodationType;

    @Column(name = "max_guests")
    @NotNull
    @Positive
    private int maxGuests;

    @Column(name = "price_per_night")
    @NotNull
    @Positive
    private int pricePerNight;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @NotNull
    private Localization localization;

    @OneToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinColumn(name = "accommodation_id")
    @Singular
    private List<Amenity> amenities;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @Column(name = "created_date")
    @Builder.Default
    private final LocalDateTime createdDate = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

    //TODO to analyze
    @OneToMany(mappedBy = "accommodation",
            orphanRemoval = true)
    private List<Booking> bookings;




}
