package com.kopec.wojciech.engineers_thesis.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Builder

@Entity
@Table(name = "ACCOMMODATIONS")

public class Accommodation extends AbstractEntity {

    @NotBlank
    @Size(min = 10, max = 150)
    private String name;

    @NotBlank
    @Size(min = 100, max = 2000)
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "localization_id")
    @NotNull
    private Localization localization;

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinColumn(name = "accommodation_id")
    @Singular
    private List<Amenity> amenities;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    public enum AccommodationType {
        FLAT, HOUSE, CABIN, RESORT, SUITE, ROOM
    }

    //Got to be implemented because of Hibernate PersistentBag lack of equals() implementation
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Accommodation that = (Accommodation) o;
        return getMaxGuests() == that.getMaxGuests() &&
                getPricePerNight() == that.getPricePerNight() &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                getAccommodationType() == that.getAccommodationType() &&
                Objects.equals(getLocalization(), that.getLocalization()) &&
                //equals() for Lists to avoid above Hibernate flaw
                getAmenities().containsAll(that.getAmenities()) &&
                that.getAmenities().containsAll(getAmenities()) &&
                Objects.equals(getUser(), that.getUser()) &&
                Objects.equals(getCreatedDate(), that.getCreatedDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName(), getDescription(), getAccommodationType(), getMaxGuests(),
                getPricePerNight(), getLocalization(), getAmenities(), getUser(), getCreatedDate());
    }
}
