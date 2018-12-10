package com.kopec.wojciech.enginners_thesis.dto;

import com.kopec.wojciech.enginners_thesis.model.Accommodation;
import com.kopec.wojciech.enginners_thesis.model.Booking;
import com.kopec.wojciech.enginners_thesis.model.User;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

import static com.kopec.wojciech.enginners_thesis.model.ModelProvider.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DtoTests {
    private Logger logger = LoggerFactory.getLogger(DtoTests.class);
    private User userOwner;
    private User userClient;
    private Accommodation accommodation1;
    private Booking booking1;

    @Before
    public void initObjects() {
        userOwner = createUser1();
        userClient = createUser2();
        accommodation1 = createAccomodation1(userOwner);
        booking1 = createBooking1(userClient, accommodation1);
    }

    @Test
    public void basicUserConversionToDTOTest() {
        UserDto userDto = UserDto.toDto(userOwner);

        assertThat(userOwner.getId(), is((userDto.getId())));
        assertThat(userOwner.getUsername(), is((userDto.getUsername())));
        assertThat(userOwner.getFirstName(), is((userDto.getFirstName())));
        assertThat(userOwner.getLastName(), is((userDto.getLastName())));
        assertThat(userOwner.getEmail(), is((userDto.getEmail())));
        assertThat(userOwner.getPhoneNumber(), is((userDto.getPhoneNumber())));
    }

    @Test
    public void deepAccomodationConversionToDTOTest() {
        AccommodationDto accommodationDto = AccommodationDto.toDto(accommodation1);
        assertThat(accommodation1.getId(), is(accommodationDto.getId()));
        assertThat(accommodation1.getName(), is(accommodationDto.getName()));
        assertThat(accommodation1.getDescription(), is(accommodationDto.getDescription()));
        assertThat(accommodation1.getAccommodationType(), is(accommodationDto.getAccommodationType()));
        assertThat(accommodation1.getPricePerNight(), is(accommodationDto.getPricePerNight()));
        assertThat(accommodation1.getMaxGuests(), is(accommodationDto.getMaxGuests()));
        assertThat(accommodation1.getCreatedDate(), is(accommodationDto.getCreatedDate()));

        assertThat(accommodation1.getLocalization(), is(LocalizationDto.toEntity(accommodationDto.getLocalization())));
        assertThat(accommodation1.getAmenities(), is(accommodationDto.getAmenities().stream()
                .map(AmenityDto::toEntity).collect(Collectors.toList())));

        accommodation1.getUser().setPassword(null); //Password is not mapped in DTO
        assertThat(accommodation1.getUser(), is(UserDto.toEntity(accommodationDto.getUser())));
    }

    @Test
    public void deepBookingConversionToDTOTest() {
        BookingDto bookingDto = BookingDto.toDto(booking1);

        assertThat(booking1.getId(), is(bookingDto.getId()));
        assertThat(booking1.getGuestsCount(), is(bookingDto.getGuestsCount()));
        assertThat(booking1.getStatus(), is(bookingDto.getStatus()));
        assertThat(booking1.getSubmissionDate(), is(bookingDto.getSubmissionDate()));
        assertThat(booking1.getStartDate(), is(bookingDto.getStartDate()));
        assertThat(booking1.getFinishDate(), is(bookingDto.getFinishDate()));
        assertThat(booking1.getFinalPrice(), is(bookingDto.getFinalPrice()));

        booking1.getUser().setPassword(null); //Password is not mapped in DTO
        assertThat(booking1.getUser(), is(UserDto.toEntity(bookingDto.getUser())));

        booking1.getAccommodation().getUser().setPassword(null); //Password is not mapped in DTO
        assertThat(booking1.getAccommodation(), is(AccommodationDto.toEntity(bookingDto.getAccommodation())));

        logger.info(booking1.toString());
    }

    @Test
    public void basicUserConversionToEntityTest() {
        UserDto userDto = UserDto.toDto(userOwner);
        User user = UserDto.toEntity(userDto);

        assertThat(user.getId(), is((userDto.getId())));
        assertThat(user.getUsername(), is((userDto.getUsername())));
        assertThat(user.getFirstName(), is((userDto.getFirstName())));
        assertThat(user.getLastName(), is((userDto.getLastName())));
        assertThat(user.getEmail(), is((userDto.getEmail())));
        assertThat(user.getPhoneNumber(), is((userDto.getPhoneNumber())));
    }


    @Test
    public void deepAccomodationConversionToEntityTest() {
        AccommodationDto accommodationDto = AccommodationDto.toDto(accommodation1);
        Accommodation accommodationEntity = AccommodationDto.toEntity(accommodationDto);

        assertThat(accommodationEntity.getId(), is(accommodationDto.getId()));
        assertThat(accommodationEntity.getName(), is(accommodationDto.getName()));
        assertThat(accommodationEntity.getDescription(), is(accommodationDto.getDescription()));
        assertThat(accommodationEntity.getAccommodationType(), is(accommodationDto.getAccommodationType()));
        assertThat(accommodationEntity.getPricePerNight(), is(accommodationDto.getPricePerNight()));
        assertThat(accommodationEntity.getMaxGuests(), is(accommodationDto.getMaxGuests()));
        assertThat(accommodationEntity.getCreatedDate(), is(accommodationDto.getCreatedDate()));

        assertThat(accommodationEntity.getLocalization(), is(LocalizationDto.toEntity(accommodationDto.getLocalization())));
        assertThat(accommodationEntity.getAmenities(), is(accommodationDto.getAmenities().stream()
                .map(AmenityDto::toEntity).collect(Collectors.toList())));

        accommodationEntity.getUser().setPassword(null); //Password is not mapped in DTO
        assertThat(accommodationEntity.getUser(), is(UserDto.toEntity(accommodationDto.getUser())));
    }

    @Test
    public void deepBookingConversionToEntityTest() {
        BookingDto bookingDto = BookingDto.toDto(booking1);
        Booking booking = BookingDto.toEntity(bookingDto);

        assertThat(booking.getId(), is(bookingDto.getId()));
        assertThat(booking.getGuestsCount(), is(bookingDto.getGuestsCount()));
        assertThat(booking.getStatus(), is(bookingDto.getStatus()));
        assertThat(booking.getSubmissionDate(), is(bookingDto.getSubmissionDate()));
        assertThat(booking.getStartDate(), is(bookingDto.getStartDate()));
        assertThat(booking.getFinishDate(), is(bookingDto.getFinishDate()));
        assertThat(booking.getFinalPrice(), is(bookingDto.getFinalPrice()));

        booking.getUser().setPassword(null); //Password is not mapped in DTO
        assertThat(booking.getUser(), is(UserDto.toEntity(bookingDto.getUser())));

        booking.getAccommodation().getUser().setPassword(null); //Password is not mapped in DTO
        assertThat(booking.getAccommodation(), is(AccommodationDto.toEntity(bookingDto.getAccommodation())));
    }

}
