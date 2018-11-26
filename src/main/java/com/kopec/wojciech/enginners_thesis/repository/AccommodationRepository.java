package com.kopec.wojciech.enginners_thesis.repository;

import com.kopec.wojciech.enginners_thesis.model.Accommodation;
import com.kopec.wojciech.enginners_thesis.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

    List<Accommodation> findAllByAccommodationType(String accommodationType);

    List<Accommodation> findAllByMaxGuestsGreaterThanEqual(int guests);

    List<Accommodation> findAllByPricePerNightBetween(int low, int high);

    List<Accommodation> findAllByLocalization_City(String city);

    List<Accommodation> findAllByAmenities(List<String> amenities);

    List<Accommodation> findAllByUser(User user);
}
