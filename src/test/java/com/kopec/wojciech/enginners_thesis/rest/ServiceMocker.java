package com.kopec.wojciech.enginners_thesis.rest;

import com.kopec.wojciech.enginners_thesis.dto.AccommodationDto;
import com.kopec.wojciech.enginners_thesis.dto.BookingDto;
import com.kopec.wojciech.enginners_thesis.model.Accommodation;
import com.kopec.wojciech.enginners_thesis.model.Booking;
import com.kopec.wojciech.enginners_thesis.service.AccommodationService;
import com.kopec.wojciech.enginners_thesis.service.BookingService;


public class ServiceMocker {

    public static BookingService mockBookingService(BookingService serviceToMock) {
        BookingDto bookingStubNullValues = BookingDto.toDto(Booking.builder().build());
        BookingRestControllerTests.setUpDTOs();
        BookingDto returnObj1 = BookingRestControllerTests.getRequestedBooking();
        BookingDto returnObj2 = BookingRestControllerTests.getExistingBooking();
        return BookingRestControllerTests.mockService(serviceToMock, returnObj1, returnObj2);
    }

    public static AccommodationService mockAccommodationService(AccommodationService serviceToMock) {
        AccommodationDto accommodationStubNullValues = AccommodationDto.toDto(Accommodation.builder().build());
        AccommodationRestControllerTests.setUpDTOs();
        AccommodationDto returnObj1 = AccommodationRestControllerTests.getRequestedAccommodation();
        AccommodationDto returnObj2 = AccommodationRestControllerTests.getExistingAccommodation();
        serviceToMock = AccommodationRestControllerTests.mockService(serviceToMock, returnObj1, returnObj2);
        return serviceToMock;
    }

}
