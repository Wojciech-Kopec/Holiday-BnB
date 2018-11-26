package com.kopec.wojciech.enginners_thesis;

import com.kopec.wojciech.enginners_thesis.model.*;
import com.kopec.wojciech.enginners_thesis.repository.AccommodationRepository;
import com.kopec.wojciech.enginners_thesis.repository.BookingRepository;
import com.kopec.wojciech.enginners_thesis.repository.UserRepository;
import org.hibernate.Hibernate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;


@SpringBootApplication
public class EnginnersThesisApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(EnginnersThesisApplication.class, args);

        User userOwner = new User();
        userOwner.setFirstName("Test_FirstName1");
        userOwner.setLastName("Test_LastName1");
        userOwner.setEmail("user1@test");
        userOwner.setPassword("password");
        userOwner.setUsername("Owner1");
        userOwner.setPhoneNumber("101010101");

        User userClient = new User();
        userClient.setFirstName("Test_FirstName2");
        userClient.setLastName("Test_LastName2");
        userClient.setEmail("user2@test");
        userClient.setPassword("password");
        userClient.setUsername("Client1");
        userClient.setPhoneNumber("121212121");

        UserRepository userRepository = context.getBean(UserRepository.class);
        userRepository.save(userOwner);
        userRepository.save(userClient);

        Accommodation accommodation1 = new Accommodation();
        accommodation1.setUser(userOwner);
        accommodation1.setName("Test_AccommodationName1");
        accommodation1.setDescription("Test_Description");
        accommodation1.setCreatedDate(LocalDateTime.now());
        accommodation1.setAccommodationType(AccommodationType.HOUSE.getType());
        accommodation1.setMaxGuests(5);
        accommodation1.setPricePerNight(500);

        Amenity amenity1 = new Amenity();
        amenity1.setType(AmenityType.WIFI.getType());
        amenity1.setDescription("Test_ Internet Connections all over the building");

        Amenity amenity2 = new Amenity();
        amenity2.setType(AmenityType.KITCHEN.getType());
        amenity2.setDescription("Test_ Fully functional kitchen");

        accommodation1.setAmenities(Arrays.asList(amenity1, amenity2));

        Localization localization1 = new Localization();
        localization1.setCountry("Poland");
        localization1.setState("Lubuskie");
        localization1.setCity("łąkie".toUpperCase());
        localization1.setAddress("Polska 52");

        accommodation1.setLocalization(localization1);

        AccommodationRepository accommodationRepository = context.getBean(AccommodationRepository.class);
        accommodationRepository.save(accommodation1);

        Booking booking = new Booking();
        booking.setUser(userClient);
        booking.setAccommodation(accommodation1);
        booking.setGuestsCount(4);
        booking.setStatus(BookingStatus.VERIFIED.toString());
        booking.setSubmissionDate(LocalDateTime.now());
        booking.setStartDate(LocalDate.of(2019, 1, 1));
        booking.setFinishDate(LocalDate.of(2019, 1, 15));
        int bookedDays = (int) DAYS.between(booking.getStartDate(), booking.getFinishDate());
        booking.setFinalPrice(booking.getAccommodation().getPricePerNight() * bookedDays);

        BookingRepository bookingRepository = context.getBean(BookingRepository.class);
        bookingRepository.save(booking);

        //TODO get() on Booking returns embedded User with bookinks=null and accommodations=null
        Hibernate.initialize(userRepository.findByUsername(userOwner.getUsername()).getAccommodations());
        User userOwnerCopy = userRepository.findByUsername(userOwner.getUsername());
        List<Accommodation> accommodationsCopy = userOwnerCopy.getAccommodations();
        accommodationsCopy.forEach(System.out::println);

        userRepository.getOne(userClient.getId()).getBookings().forEach(System.out::println);
    }
}
