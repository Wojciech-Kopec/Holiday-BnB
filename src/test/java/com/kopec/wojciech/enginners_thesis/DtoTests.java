package com.kopec.wojciech.enginners_thesis;

import com.kopec.wojciech.enginners_thesis.dto.*;
import com.kopec.wojciech.enginners_thesis.model.Accommodation;
import com.kopec.wojciech.enginners_thesis.model.Booking;
import com.kopec.wojciech.enginners_thesis.model.User;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

import static com.kopec.wojciech.enginners_thesis.EnginnersThesisApplication.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DtoTests {
    private static Logger logger = LoggerFactory.getLogger(DtoTests.class);
    private User userOwner;
    private User userClient;
    private Accommodation accommodation1;
    private Booking booking1;

    @Before
    public void initObjects() {
        userOwner = createOwnerUser();
        userClient = createClientUser();
        accommodation1 = createAccomodationObject(userOwner);
        booking1 = createBooking(userClient, accommodation1);
    }

    @Test
    public void basicUserDtoConversionTest() {
        UserDto userDto = new UserDto().toDto(userOwner);

        assertThat(userOwner.getId(), is((userDto.getId())));
        assertThat(userOwner.getUsername(), is((userDto.getUsername())));
        assertThat(userOwner.getFirstName(), is((userDto.getFirstName())));
        assertThat(userOwner.getLastName(), is((userDto.getLastName())));
        assertThat(userOwner.getEmail(), is((userDto.getEmail())));
        assertThat(userOwner.getPhoneNumber(), is((userDto.getPhoneNumber())));
    }

    @Test
    public void deepAccomodationDtoConversionTest() {
        AccommodationDto accommodationDto = new AccommodationDto().toDto(accommodation1);
        assertThat(accommodation1.getId(), is(accommodationDto.getId()));
        assertThat(accommodation1.getName(), is(accommodationDto.getName()));
        assertThat(accommodation1.getDescription(), is(accommodationDto.getDescription()));
        assertThat(accommodation1.getAccommodationType(), is(accommodationDto.getAccommodationType()));
        assertThat(accommodation1.getPricePerNight(), is(accommodationDto.getPricePerNight()));
        assertThat(accommodation1.getMaxGuests(), is(accommodationDto.getMaxGuests()));
        assertThat(accommodation1.getCreatedDate(), is(accommodationDto.getCreatedDate()));

        assertThat(accommodation1.getLocalization(), is(accommodationDto.getLocalization().toEntity()));
        assertThat(accommodation1.getAmenities(), is(accommodationDto.getAmenities().stream()
                .map(AmenityDto::toEntity).collect(Collectors.toList())));

        accommodation1.getUser().setPassword(null); //Password is not mapped in DTO
        assertThat(accommodation1.getUser(), is(accommodationDto.getUser().toEntity()));
    }

    @Test
    public void deepBookingDtoConversionTest() {
        BookingDto bookingDto = new BookingDto().toDto(booking1);

        assertThat(booking1.getId(), is(bookingDto.getId()));
        assertThat(booking1.getGuestsCount(), is(bookingDto.getGuestsCount()));
        assertThat(booking1.getStatus(), is(bookingDto.getStatus()));
        assertThat(booking1.getSubmissionDate(), is(bookingDto.getSubmissionDate()));
        assertThat(booking1.getStartDate(), is(bookingDto.getStartDate()));
        assertThat(booking1.getFinishDate(), is(bookingDto.getFinishDate()));
        assertThat(booking1.getFinalPrice(), is(bookingDto.getFinalPrice()));

        booking1.getUser().setPassword(null); //Password is not mapped in DTO
        assertThat(booking1.getUser(), is(bookingDto.getUser().toEntity()));

        booking1.getAccommodation().getUser().setPassword(null); //Password is not mapped in DTO
        assertThat(booking1.getAccommodation(), is(bookingDto.getAccommodation().toEntity()));
    }
}
