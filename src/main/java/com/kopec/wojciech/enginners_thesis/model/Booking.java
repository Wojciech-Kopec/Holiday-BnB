package com.kopec.wojciech.enginners_thesis.model;


import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static java.time.temporal.ChronoUnit.DAYS;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)

@Entity
@Table(name = "BOOKINGS")

public class Booking extends AbstractEntity {

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "accommodation_id")
    @NotNull
    private Accommodation accommodation;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
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
    private LocalDateTime submissionDate;

    @Column(name = "start_date")
    @Future
    @NotNull
    private LocalDate startDate;

    @Column(name = "finish_date")
    @Future
    @NotNull
    private LocalDate finishDate;

    @Column(name = "final_price")
    @NotNull
    private int finalPrice;

    public enum BookingStatus {
        VERIFIED, SUBMITTED, REJECTED
    }

    @AssertTrue(message = "Finish date must be after start date")
    private boolean isFinishDateAfterStartDate() {
        return this.startDate != null && this.finishDate != null
                && this.finishDate.isAfter(this.startDate);
    }

    public Booking() {
        this.status = BookingStatus.SUBMITTED;
        if (startDate != null && finishDate != null && accommodation != null)
            calculateFinalPrice();
    }

    @Builder
    public Booking(Accommodation accommodation, User user, int guestsCount, LocalDate startDate, LocalDate
            finishDate, BookingStatus status, LocalDateTime submissionDate) {
        this.accommodation = accommodation;
        this.user = user;
        this.guestsCount = guestsCount;
        this.status = status;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.submissionDate = submissionDate;
        calculateFinalPrice();
    }

    public void calculateFinalPrice() {
        this.finalPrice = isFinishDateAfterStartDate() ?
                (int) (DAYS.between(startDate, finishDate) * accommodation.getPricePerNight()) : -1;
    }
}
