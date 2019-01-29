package com.kopec.wojciech.enginners_thesis.rest;

import com.kopec.wojciech.enginners_thesis.dto.AccommodationDto;
import com.kopec.wojciech.enginners_thesis.dto.BookingDto;
import com.kopec.wojciech.enginners_thesis.dto.UserDto;
import com.kopec.wojciech.enginners_thesis.model.Accommodation;
import com.kopec.wojciech.enginners_thesis.model.Booking;
import com.kopec.wojciech.enginners_thesis.service.AccommodationService;
import com.kopec.wojciech.enginners_thesis.service.BookingService;
import com.kopec.wojciech.enginners_thesis.service.UserService;


public class ServiceMocker {

    public static BookingService mockBookingService(BookingService serviceToMock) {
        BookingRestControllerTests.setUpDTOs();
        return BookingRestControllerTests.mockService(serviceToMock,
                BookingRestControllerTests.getRequestedBooking(), BookingRestControllerTests.getExistingBooking());
    }

    public static AccommodationService mockAccommodationService(AccommodationService serviceToMock) {
        AccommodationRestControllerTests.setUpDTOs();
        AccommodationDto returnObj1 = AccommodationRestControllerTests.getRequestedAccommodation();
        AccommodationDto returnObj2 = AccommodationRestControllerTests.getExistingAccommodation();
        return AccommodationRestControllerTests.mockService(serviceToMock, returnObj1, returnObj2);
    }


//    public static UserService mockUserService(UserService serviceToMock) {
//        UserRestControllerTests.setUpDTOs();
//        UserDto returnObj1 = UserRestControllerTests.getRequestedUser();
//        UserDto returnObj2 = UserRestControllerTests.getExistingUser();
//        return UserRestControllerTests.mockService(serviceToMock, returnObj1, returnObj2);
//    }

    public static UserDto getPrimaryUserDto() {
        return null;
    }

    public static UserDto getSecondaryUserDto() {
        return null;
    }

    public static AccommodationDto getPrimaryAccommodationDto() {
        return null;
    }

    public static AccommodationDto getSecondaryAccommodatioDto() {
        return null;
    }

    public static BookingDto getPrimaryBookingDto() {
        return null;
    }

    public static BookingDto getSecondaryBookingDto() {
        return null;
    }

}
