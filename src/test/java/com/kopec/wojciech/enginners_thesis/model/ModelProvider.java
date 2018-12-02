package com.kopec.wojciech.enginners_thesis.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import static java.time.temporal.ChronoUnit.DAYS;

public class ModelProvider {

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

    public static Accommodation createAccomodationObject(User userOwner) {
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
        localization1.setCity("Łąkie");
        localization1.setAddress("Polska 52");

        accommodation1.setLocalization(localization1);
        return accommodation1;
    }

    public static Booking createBooking(User userClient, Accommodation accommodation1) {
        Booking booking1 = new Booking();
        booking1.setUser(userClient);
        booking1.setAccommodation(accommodation1);
        booking1.setGuestsCount(4);
        booking1.setStatus(BookingStatus.VERIFIED.toString());
        booking1.setSubmissionDate(LocalDateTime.now());
        booking1.setStartDate(LocalDate.of(2019, 1, 1));
        booking1.setFinishDate(LocalDate.of(2019, 1, 15));
        int bookedDays = (int) DAYS.between(booking1.getStartDate(), booking1.getFinishDate());
        booking1.setFinalPrice(booking1.getAccommodation().getPricePerNight() * bookedDays);
        return booking1;
    }
}
