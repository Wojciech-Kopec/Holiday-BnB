package com.kopec.wojciech.engineers_thesis.utils;

import com.kopec.wojciech.engineers_thesis.model.*;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class ModelProvider {

    public static User createUser_1_noId() {
        return User.builder()
                .firstName("Test_FirstName1")
                .lastName("Test_LastName1")
                .email("user1@test.com")
                .password("password1")
                .username("User1")
                .phoneNumber("101010101")
                .password("password")
                .build();
    }


    public static User createUser_2_noId() {
        return User.builder()
                .firstName("Test_FirstName2")
                .lastName("Test_LastName2")
                .email("user2@test.com")
                .password("password2")
                .username("User2")
                .phoneNumber("121212121")
                .password("password")
                .build();
    }

    public static Accommodation createAccommodation_1_noId(User userOwner) {
        Accommodation accommodation;

        Amenity amenity1 = Amenity.builder()
                .type(Amenity.AmenityType.WIFI)
                .description("Internet Connections all over the building")
                .build();

        Amenity amenity2 = Amenity.builder()
                .type(Amenity.AmenityType.KITCHEN)
                .description("Fully functional kitchen")
                .build();

        Localization localization1 = Localization.builder()
                .country("Poland")
                .city("Łąkie")
                .address("Łąkie 52")
                .build();

        accommodation = Accommodation.builder()
                .user(userOwner)
                .name("Test_AccommodationName1")
                .description("Test_Description1_" + StringUtils.repeat("A", 100))
                .accommodationType(Accommodation.AccommodationType.HOUSE)
                .maxGuests(5)
                .pricePerNight(500)
                .amenities(new LinkedList<>(Arrays.asList(amenity1, amenity2)))
                .localization(localization1)
                .createdDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES))
                .build();

        return accommodation;
    }

    public static Accommodation createAccommodation_2_noId(User userOwner) {
        Accommodation accommodation;

        Amenity amenity1 = Amenity.builder()
                .type(Amenity.AmenityType.BACKYARD)
                .description("Spacious yard just behind the property")
                .build();

        Amenity amenity2 = Amenity.builder()
                .type(Amenity.AmenityType.TV)
                .description("50 inch TV set with cable in property")
                .build();

        Amenity amenity3 = Amenity.builder()
                .type(Amenity.AmenityType.OTHER)
                .description("Roof of property is fully accessible for guests")
                .build();

        Localization localization2 = Localization.builder()
                .country("Poland")
                .city("Wrocław")
                .address("Rynek 10")
                .build();

        accommodation = Accommodation.builder()
                .user(userOwner)
                .name("Test_AccommodationName2")
                .description("Test_Description2_" + StringUtils.repeat("A", 100))
                .accommodationType(Accommodation.AccommodationType.FLAT)
                .maxGuests(4)
                .pricePerNight(350)
                .amenities(new LinkedList<>(Arrays.asList(amenity1, amenity2, amenity3)))
                .localization(localization2)
                .createdDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES))
                .build();

        return accommodation;
    }

    public static Booking createBooking_1_noId(User client, Accommodation accommodation) {
        return Booking.builder()
                .user(client)
                .accommodation(accommodation)
                .guestsCount(4)
                .status(Booking.BookingStatus.VERIFIED)
                .startDate(LocalDate.of(2022, 1, 1))
                .finishDate(LocalDate.of(2022, 1, 15))
                .submissionDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES))
                .build();
    }

    public static Booking createBooking_2_noId(User client, Accommodation accommodation) {
        return Booking.builder()
                .user(client)
                .accommodation(accommodation)
                .guestsCount(3)
                .status(Booking.BookingStatus.SUBMITTED)
                .startDate(LocalDate.of(2022, 2, 10))
                .finishDate(LocalDate.of(2022, 3, 1))
                .submissionDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES))
                .build();
    }

    public static User createUser_1() {
        User user = createUser_1_noId();
        user.setId(1);
        return user;
    }


    public static User createUser_2() {
        User user = createUser_2_noId();
        user.setId(2);
        return user;
    }

    public static Accommodation createAccommodation_1(User userOwner) {
        Accommodation accommodation = createAccommodation_1_noId(userOwner);
        accommodation.getLocalization().setId(1);

        for (int i = 0; i < accommodation.getAmenities().size(); i++) {
            accommodation.getAmenities().get(i).setId(i+1); //Id must be positive
        }

        accommodation.setId(1);
        return accommodation;
    }

    public static Accommodation createAccommodation_2(User userOwner) {
        Accommodation accommodation = createAccommodation_2_noId(userOwner);
        accommodation.getLocalization().setId(2);

        final int primaryAccommodationAmenitiesSize = 2; //For keeping IDs unique
        for (int i = 0; i < accommodation.getAmenities().size(); i++) {
            accommodation.getAmenities().get(i).setId(i+1+primaryAccommodationAmenitiesSize); //Id must be positive
        }

        accommodation.setId(2);
        return accommodation;
    }

    public static Booking createBooking_1(User client, Accommodation accommodation) {
        Booking booking = createBooking_1_noId(client,accommodation);
        booking.setId(1);
        return booking;
    }

    public static Booking createBooking_2(User client, Accommodation accommodation) {
        Booking booking = createBooking_1_noId(client,accommodation);
        booking.setId(2);
        return booking;
    }
}
