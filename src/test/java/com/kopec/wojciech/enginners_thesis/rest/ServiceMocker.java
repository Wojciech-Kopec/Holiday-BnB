package com.kopec.wojciech.enginners_thesis.rest;

import com.kopec.wojciech.enginners_thesis.dto.AccommodationDto;
import com.kopec.wojciech.enginners_thesis.dto.BookingDto;
import com.kopec.wojciech.enginners_thesis.dto.UserDto;
import com.kopec.wojciech.enginners_thesis.service.AccommodationService;
import com.kopec.wojciech.enginners_thesis.service.BookingService;
import com.kopec.wojciech.enginners_thesis.service.UserService;

import static com.kopec.wojciech.enginners_thesis.utils.ModelProvider.*;

/** This class contains of static methods which return Mock Instance of corresponding
 * com.kopec.wojciech.enginners_thesis.service Classes. Mocks are stubbed with default instances of Model classes */
public class ServiceMocker {

    public static UserService mockUserService(UserService serviceToMock) {
        return UserRestControllerTests.mockService(
                serviceToMock,
                buildPrimaryUserDto(),
                buildSecondaryUserDto());
    }

    public static AccommodationService mockAccommodationService(AccommodationService serviceToMock) {
        return AccommodationRestControllerTests.mockService(
                serviceToMock,
                buildPrimaryAccommodationDto(), buildSecondaryAccommodationDto());
    }

    public static BookingService mockBookingService(BookingService serviceToMock) {
        return BookingRestControllerTests.mockService(
                serviceToMock,
                buildPrimaryBookingDto(),
                buildSecondaryBookingDto());
    }


    public static UserDto buildPrimaryUserDto() {
        return UserDto.toDto(createUser_1());
    }

    public static UserDto buildSecondaryUserDto() {
        return UserDto.toDto(createUser_2());
    }

    public static AccommodationDto buildPrimaryAccommodationDto() {
        return AccommodationDto.toDto(createAccommodation_1(createUser_1()));
    }

    public static AccommodationDto buildSecondaryAccommodationDto() {
        return AccommodationDto.toDto(createAccommodation_2(createUser_2()));
    }

    public static BookingDto buildPrimaryBookingDto() {
        return BookingDto.toDto(createBooking_1(createUser_2(), createAccommodation_1(createUser_1())));
    }

    public static BookingDto buildSecondaryBookingDto() {
        return BookingDto.toDto(createBooking_2(createUser_1(), createAccommodation_2(createUser_2())));
    }
}
