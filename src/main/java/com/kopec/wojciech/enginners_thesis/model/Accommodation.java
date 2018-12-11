package com.kopec.wojciech.enginners_thesis.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode(exclude = {"bookings","amenities"}, callSuper = false)
@ToString(exclude = "bookings")
@Builder

@Entity
@Table(name = "ACCOMMODATIONS")

public class Accommodation extends AbstractEntity {

    @NotNull
    @Size(min = 10, max = 120)
    private String name;

    @NotBlank //todo
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
    @JoinColumn(name = "amenity_id")
    @Singular
    private List<Amenity> amenities;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @Column(name = "created_date")
    @Builder.Default
    private final LocalDateTime createdDate = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);


    @OneToMany(mappedBy = "accommodation",
            orphanRemoval = true)
    @Singular
    private List<Booking> bookings;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Accommodation that = (Accommodation) o;

        if (maxGuests != that.maxGuests) return false;
        if (pricePerNight != that.pricePerNight) return false;
        if (!name.equals(that.name)) return false;
        if (!description.equals(that.description)) return false;
        if (accommodationType != that.accommodationType) return false;
        if (!localization.equals(that.localization)) return false;
        if (!amenities.containsAll(that.amenities) && that.amenities.containsAll(amenities)) return false;
        if (!user.equals(that.user)) return false;
        return createdDate.equals(that.createdDate);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + accommodationType.hashCode();
        result = 31 * result + maxGuests;
        result = 31 * result + pricePerNight;
        result = 31 * result + localization.hashCode();
        result = 31 * result + amenities.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + createdDate.hashCode();
        return result;
    }
}
