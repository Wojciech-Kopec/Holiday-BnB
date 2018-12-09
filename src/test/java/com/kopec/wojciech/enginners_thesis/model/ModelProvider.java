package com.kopec.wojciech.enginners_thesis.model;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class ModelProvider {

    public static User createOwnerUser() {
        return User.builder()
                .firstName("Test_FirstName1")
                .lastName("Test_LastName1")
                .email("user1@test.com")
                .password("password1")
                .username("Owner1")
                .phoneNumber("101010101")
                .build();
    }


    public static User createClientUser() {
        return User.builder()
                .firstName("Test_FirstName2")
                .lastName("Test_LastName2")
                .email("user2@test.com")
                .password("password2")
                .username("Client1")
                .phoneNumber("121212121")
                .build();
    }

    public static Accommodation createAccomodationObject1(User userOwner) {
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
                .description("Test_Description" + StringUtils.repeat(" ", 100))
                .accommodationType(AccommodationType.HOUSE)
                .maxGuests(5)
                .pricePerNight(500)
                .amenities(Arrays.asList(amenity1, amenity2))
                .localization(localization1)
                .build();

        return accommodation;
    }

    public static Booking createBooking(User userClient, Accommodation accommodation1) {
        return Booking.builder()
                .user(userClient)
                .accommodation(accommodation1)
                .guestsCount(4)
                .status(BookingStatus.VERIFIED)
                .startDate(LocalDate.of(2019, 1, 1))
                .finishDate(LocalDate.of(2019, 1, 15))
                .build();
    }
}
