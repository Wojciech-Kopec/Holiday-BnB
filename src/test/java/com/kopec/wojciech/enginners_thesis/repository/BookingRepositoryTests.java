package com.kopec.wojciech.enginners_thesis.repository;

import com.kopec.wojciech.enginners_thesis.model.Accommodation;
import com.kopec.wojciech.enginners_thesis.model.Booking;
import com.kopec.wojciech.enginners_thesis.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static com.kopec.wojciech.enginners_thesis.model.ModelProvider.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookingRepositoryTests implements TestableRepository<Booking, BookingRepository> {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    AccommodationRepository accommodationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRepositoryTests userRepositoryTests;

    @Autowired
    AccommodationRepositoryTests accommodationRepositoryTests;

    private User user1 = createUser_1();
    private User user2 = createUser_2();
    private Accommodation accommodation1 = createAccommodation_1(user1);
    private Accommodation accommodation2 = createAccommodation_2(user2);
    private Booking booking1 = createBooking_1(user2, accommodation1);
    private Booking booking2 = createBooking_2(user1, accommodation2);


    @Before
    public void persistRequired() {
        userRepositoryTests.createEntityTest(user1, userRepository);
        userRepositoryTests.createEntityTest(user2, userRepository);
        accommodationRepositoryTests.createEntityTest(accommodation1, accommodationRepository);
    }

    @Test
    public void createBookingEntityTest() {
        createEntityTest(booking1, bookingRepository);
    }

    @Test
    @Transactional
    public void readBookingEntityTest() {
        readEntityTest(booking1, bookingRepository);
    }

    @Test
    @Transactional
    //TODO Got to be there to work with booking.accommodation.localization persistence, why?
    //TODO Make Booking Immutable? Should any update be available?
    public void updateBookingEntityTest() {
        accommodationRepositoryTests.createEntityTest(accommodation2, accommodationRepository);
        updateEntityTest(booking1, booking2, bookingRepository);
    }

    @Test
    public void deleteBookingEntityTest() {
        deleteEntityTest(booking1, bookingRepository);
    }


    @After
    @Override
    public void wipe() {
        bookingRepository.deleteAll();
        accommodationRepository.deleteAll();
        userRepository.deleteAll();
    }
}
