package com.kopec.wojciech.enginners_thesis.repository;

import com.kopec.wojciech.enginners_thesis.model.Accommodation;
import com.kopec.wojciech.enginners_thesis.model.AccommodationType;
import com.kopec.wojciech.enginners_thesis.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.function.Predicate;

@RepositoryRestResource()
public interface AccommodationRepository extends JpaRepository<Accommodation, Integer>, QuerydslPredicateExecutor<Accommodation> {

    List<Accommodation> findAllByAccommodationType(AccommodationType accommodationType);

    List<Accommodation> findAllByMaxGuestsGreaterThanEqual(int guests);

    List<Accommodation> findAllByPricePerNightBetween(int low, int high);

    List<Accommodation> findAllByLocalization_City(String city);

    List<Accommodation> findAllByAmenities(List<String> amenities);

    List<Accommodation> findAllByUser(User user);
}
