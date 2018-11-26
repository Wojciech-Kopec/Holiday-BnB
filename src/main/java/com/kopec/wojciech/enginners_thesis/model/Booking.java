package com.kopec.wojciech.enginners_thesis.model;


import lombok.*;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
//@ToString

@Entity
@Table(name = "BOOKINGS")

public class Booking implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private long id;
    @ManyToOne
    @JoinColumn(name = "accomodation_id")
    private Accommodation accommodation;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;
    private int guestsCount;
    private String status;
    private LocalDateTime submissionDate;
    private LocalDate startDate;
    private LocalDate finishDate;
    private int finalPrice;
}
