package com.kopec.wojciech.enginners_thesis.model;

import lombok.*;
import org.hibernate.annotations.IndexColumn;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode(exclude = {"bookings"}, callSuper = true)
@ToString(exclude = "bookings", callSuper = true)
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

    @OneToOne(cascade = CascadeType.MERGE)
    @MapsId
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
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @IndexColumn(name = "id", base = 1)
    @Singular
    private List<Booking> bookings;

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
        return Objects.hash(super.hashCode(), getName(), getDescription(), getAccommodationType(), getMaxGuests(), getPricePerNight(), getLocalization(), getAmenities(), getUser(), getCreatedDate());
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings.clear();
        if(bookings != null) {
            this.bookings.addAll(bookings);
        }
    }
}
