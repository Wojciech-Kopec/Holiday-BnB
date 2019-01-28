package com.kopec.wojciech.enginners_thesis.repository;

import com.kopec.wojciech.enginners_thesis.model.Accommodation;
import com.kopec.wojciech.enginners_thesis.model.Booking;
import com.kopec.wojciech.enginners_thesis.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource()
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    List<Booking> findAllByAccommodationOrderBySubmissionDateDesc(Accommodation accommodation);

    List<Booking> findAllByAccommodation_IdOrderBySubmissionDateDesc(Integer accommodationId);

    List<Booking> findAllByUserOrderBySubmissionDateDesc(User user);
}
