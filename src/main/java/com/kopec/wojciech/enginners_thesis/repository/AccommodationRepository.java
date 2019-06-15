package com.kopec.wojciech.enginners_thesis.repository;

import com.kopec.wojciech.enginners_thesis.model.Accommodation;
import com.kopec.wojciech.enginners_thesis.model.AccommodationType;
import com.kopec.wojciech.enginners_thesis.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Predicate;

import static javafx.scene.input.KeyCode.T;

@RepositoryRestResource()
public interface AccommodationRepository extends JpaRepository<Accommodation, Integer>, QuerydslPredicateExecutor<Accommodation> {

    default Accommodation saveEntity(Accommodation accommodation) {
        if(accommodation.getId() == null)
        accommodation.setCreatedDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        return save(accommodation);
    }

    List<Accommodation> findAllByUserOrderByCreatedDateDesc(User user);
}
