package com.kopec.wojciech.enginners_thesis.model;


import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static java.time.temporal.ChronoUnit.DAYS;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString()

@Entity
@Table(name = "BOOKINGS")

public class Booking extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    @NotNull
    private Accommodation accommodation;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @Column(name = "guests_count")
    @Positive
    @NotNull
    private int guestsCount;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @Column(name = "submission_date")
    @Builder.Default
    private final LocalDateTime submissionDate;

    @Column(name = "start_date")
    @Future
    @NotNull
    private LocalDate startDate;

    @Column(name = "finish_date")
    @Future
    @NotNull
    private LocalDate finishDate;

    @Column(name = "final_price")
    @Positive
    @NotNull
    private int finalPrice;

    @AssertTrue(message = "Finish date must be after start date")
    private boolean isFinishDateAfterStartDate() {
        return this.startDate != null && this.finishDate != null
                && this.finishDate.isAfter(this.startDate);
    }

    public Booking() {
        this.submissionDate = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        this.status = BookingStatus.SUBMITTED;
        if (startDate != null && finishDate != null && accommodation != null)
            this.finalPrice = isFinishDateAfterStartDate() ?
                    (int) (DAYS.between(startDate, finishDate) * accommodation.getPricePerNight()) : -1;
    }

    @Builder
    public Booking(Accommodation accommodation, User user, int guestsCount, LocalDate startDate, LocalDate finishDate, BookingStatus status /*TODO to remove?*/) {
        this.accommodation = accommodation;
        this.user = user;
        this.guestsCount = guestsCount;
        this.status = status;
        this.submissionDate = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.finalPrice = isFinishDateAfterStartDate() ?
                (int) (DAYS.between(startDate, finishDate) * accommodation.getPricePerNight()) : -1;
    }
}
