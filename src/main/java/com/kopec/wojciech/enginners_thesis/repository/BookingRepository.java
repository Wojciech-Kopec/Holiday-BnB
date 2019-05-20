package com.kopec.wojciech.enginners_thesis.repository;

import com.kopec.wojciech.enginners_thesis.model.Accommodation;
import com.kopec.wojciech.enginners_thesis.model.Booking;
import com.kopec.wojciech.enginners_thesis.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RepositoryRestResource()
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    default Booking saveEntity(Booking booking) {
        if(booking.getSubmissionDate() == null)
            booking.setSubmissionDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        booking.calculateFinalPrice();
        return save(booking);
    }

    List<Booking> findAllByAccommodationOrderBySubmissionDateDesc(Accommodation accommodation);

    List<Booking> findAllByAccommodation_IdOrderBySubmissionDateDesc(Integer accommodationId);

    List<Booking> findAllByUserOrderBySubmissionDateDesc(User user);
}
