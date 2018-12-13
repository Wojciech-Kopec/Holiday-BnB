package com.kopec.wojciech.enginners_thesis.model;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

public class ModelProvider {

    public static User createUser1() {
        return User.builder()
                .firstName("Test_FirstName1")
                .lastName("Test_LastName1")
                .email("user1@test.com")
                .password("password1")
                .username("User1")
                .phoneNumber("101010101")
                .build();
    }


    public static User createUser2() {
        return User.builder()
                .firstName("Test_FirstName2")
                .lastName("Test_LastName2")
                .email("user2@test.com")
                .password("password2")
                .username("User2")
                .phoneNumber("121212121")
                .build();
    }

    public static Accommodation createAccomodation1(User userOwner) {
        Accommodation accommodation;

        Amenity amenity1 = Amenity.builder()
                .type(AmenityType.WIFI)
                .description("Internet Connections all over the building")
                .build();

        Amenity amenity2 = Amenity.builder()
                .type(AmenityType.KITCHEN)
                .description("Fully functional kitchen")
                .build();

        Localization localization1 = Localization.builder()
                .country("Poland")
                .state("Lubuskie")
                .city("Łąkie")
                .address("Łąkie 52")
                .build();

        accommodation = Accommodation.builder()
                .user(userOwner)
                .name("Test_AccommodationName1")
                .description("Test_Description1_" + StringUtils.repeat(".", 100))
                .accommodationType(AccommodationType.HOUSE)
                .maxGuests(5)
                .pricePerNight(500)
                .amenities(new LinkedList<>(Arrays.asList(amenity1, amenity2)))
                .localization(localization1)
                .build();

        return accommodation;
    }

    public static Accommodation createAccomodation2(User userOwner) {
        Accommodation accommodation;

        Amenity amenity1 = Amenity.builder()
                .type(AmenityType.BACKYARD)
                .description("Spacious yard just behind the property")
                .build();

        Amenity amenity2 = Amenity.builder()
                .type(AmenityType.TV)
                .description("50 inch TV set with cable in property")
                .build();

        Amenity amenity3 = Amenity.builder()
                .type(AmenityType.OTHER)
                .description("Roof of property is fully accessible for guests")
                .build();

        Localization localization2 = Localization.builder()
                .country("Poland")
                .state("Dolnoslaskie")
                .city("Wrocław")
                .address("Rynek 10")
                .build();

        accommodation = Accommodation.builder()
                .user(userOwner)
                .name("Test_AccommodationName2")
                .description("Test_Description2_" + StringUtils.repeat(".", 100))
                .accommodationType(AccommodationType.FLAT)
                .maxGuests(4)
                .pricePerNight(350)
                .amenities(new LinkedList<>(Arrays.asList(amenity1, amenity2, amenity3)))
                .localization(localization2)
                .build();

        return accommodation;
    }

    public static Booking createBooking1(User client, Accommodation accommodation) {
        return Booking.builder()
                .user(client)
                .accommodation(accommodation)
                .guestsCount(4)
                .status(BookingStatus.VERIFIED)
                .startDate(LocalDate.of(2019, 1, 1))
                .finishDate(LocalDate.of(2019, 1, 15))
                .build();
    }

    public static Booking createBooking2(User client, Accommodation accommodation) {
        return Booking.builder()
                .user(client)
                .accommodation(accommodation)
                .guestsCount(3)
                .status(BookingStatus.SUBMITTED)
                .startDate(LocalDate.of(2019, 2, 10))
                .finishDate(LocalDate.of(2019, 3, 1))
                .build();
    }
}
