package com.kopec.wojciech.enginners_thesis;

import com.kopec.wojciech.enginners_thesis.model.*;
import com.kopec.wojciech.enginners_thesis.repository.AccommodationRepository;
import com.kopec.wojciech.enginners_thesis.repository.BookingRepository;
import com.kopec.wojciech.enginners_thesis.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.time.temporal.ChronoUnit.DAYS;


@SpringBootApplication
public class EnginnersThesisApplication {
    private static final Logger logger = LoggerFactory.getLogger(EnginnersThesisApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(EnginnersThesisApplication.class, args);


        User userOwner = createOwnerUser();
        User userClient = createClientUser();

        logger.info("Adding created User-objects");
        UserRepository userRepository = context.getBean(UserRepository.class);
        userRepository.save(userOwner);
        userRepository.save(userClient);
        logger.info("Objects added");

        Accommodation accommodation1 = createAccomodationObject(userOwner);

        logger.info("Adding created Accommodation-objects");
        AccommodationRepository accommodationRepository = context.getBean(AccommodationRepository.class);
        accommodationRepository.save(accommodation1);
        logger.info("Objects added");

        Booking booking1 = createBooking(userClient, accommodation1);

        logger.info("Adding created Booking-objects");
        BookingRepository bookingRepository = context.getBean(BookingRepository.class);
        bookingRepository.save(booking1);
        logger.info("Objects added");

        logger.info("userRepository.findByUsername(userOwner.getUsername()).getAccommodations().forEach(System.out::println);");
        User userOwnerCopy = userRepository.findByUsername(userOwner.getUsername());
        List<Accommodation> accommodationsCopy = userOwnerCopy.getAccommodations();
        accommodationsCopy.forEach(accommodation -> logger.info(accommodation.toString()));

        logger.info("userRepository.findByUsername(userClient.getUsername()).getBookings().forEach(System.out::println);");
        userRepository.findByUsername(userClient.getUsername()).getBookings().forEach(booking -> logger.info(booking.toString()));

//        accommodationRepository.findAll(QAccommodation.accommodation.name.contains(criteria.getName()));


    }

    public static Booking createBooking(User userClient, Accommodation accommodation1) {
        Booking booking1 = new Booking();
        booking1.setUser(userClient);
        booking1.setAccommodation(accommodation1);
        booking1.setGuestsCount(4);
        booking1.setStatus(BookingStatus.VERIFIED);
        booking1.setStartDate(LocalDate.of(2019, 1, 1));
        booking1.setFinishDate(LocalDate.of(2019, 1, 15));
        int bookedDays = (int) DAYS.between(booking1.getStartDate(), booking1.getFinishDate());
        booking1.setFinalPrice(booking1.getAccommodation().getPricePerNight() * bookedDays);
        return booking1;
    }

    public static Accommodation createAccomodationObject(User userOwner) {
        Accommodation accommodation1 = new Accommodation();
        accommodation1.setUser(userOwner);
        accommodation1.setName("Test_AccommodationName1");
        accommodation1.setDescription("Test_Description");
        accommodation1.setAccommodationType(AccommodationType.HOUSE);
        accommodation1.setMaxGuests(5);
        accommodation1.setPricePerNight(500);

        Amenity amenity1 = new Amenity();
        amenity1.setType(AmenityType.WIFI);
        amenity1.setDescription("Test_ Internet Connections all over the building");

        Amenity amenity2 = new Amenity();
        amenity2.setType(AmenityType.KITCHEN);
        amenity2.setDescription("Test_ Fully functional kitchen");

        accommodation1.setAmenities(Arrays.asList(amenity1, amenity2));

        Localization localization1 = new Localization();
        localization1.setCountry("Poland");
        localization1.setState("Lubuskie");
        localization1.setCity("Łąkie");
        localization1.setAddress("Polska 52");

        accommodation1.setLocalization(localization1);
        return accommodation1;
    }

    public static User createClientUser() {
        User userClient = new User();
        userClient.setFirstName("Test_FirstName2");
        userClient.setLastName("Test_LastName2");
        userClient.setEmail("user2@test");
        userClient.setPassword("password2");
        userClient.setUsername("Client1");
        userClient.setPhoneNumber("121212121");
        return userClient;
    }

    public static User createOwnerUser() {
        User userOwner = new User();
        userOwner.setFirstName("Test_FirstName1");
        userOwner.setLastName("Test_LastName1");
        userOwner.setEmail("user1@test");
        userOwner.setPassword("password1");
        userOwner.setUsername("Owner1");
        userOwner.setPhoneNumber("101010101");
        return userOwner;
    }

}
