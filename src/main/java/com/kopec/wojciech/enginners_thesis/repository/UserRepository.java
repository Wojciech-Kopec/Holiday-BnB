package com.kopec.wojciech.enginners_thesis.repository;

import com.kopec.wojciech.enginners_thesis.model.Accommodation;
import com.kopec.wojciech.enginners_thesis.model.Booking;
import com.kopec.wojciech.enginners_thesis.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource()
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT user FROM User user left join fetch user.accommodations left join fetch user.bookings WHERE user.username=:username")
    User findByUsername(@Param("username") String username);

    User findByAccommodationsContains(Accommodation accommodation);

    User findByBookingsContains(Booking booking);


}
