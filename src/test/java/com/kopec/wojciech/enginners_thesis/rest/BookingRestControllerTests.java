package com.kopec.wojciech.enginners_thesis.rest;

import com.google.common.collect.Lists;
import com.kopec.wojciech.enginners_thesis.dto.BookingDto;
import com.kopec.wojciech.enginners_thesis.service.BookingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;

@RunWith(SpringRunner.class)
@WebMvcTest(value = BookingRestController.class, secure = false)
public class BookingRestControllerTests extends AbstractRestMockedTest {

    @MockBean
    private BookingService bookingService;

    @InjectMocks
    private BookingRestController bookingRestController;

    private static String baseEndpoint = BookingRestController.class.getAnnotation(RequestMapping.class).value()[0];
    private BookingDto requestedBooking;
    private BookingDto anotherBooking;


    public void mockServices() {
        bookingService = mockService(bookingService, requestedBooking, anotherBooking);
        bookingRestController = new BookingRestController(bookingService);
    }

    public static BookingService mockService(BookingService bookingService, BookingDto primaryBooking, BookingDto secondaryBooking) {

        mockUnitMethodsForInstance(bookingService, primaryBooking);
        mockUnitMethodsForInstance(bookingService, secondaryBooking);

        Mockito.when(bookingService.findAll())
                .thenReturn(Lists.newArrayList(primaryBooking, secondaryBooking));

        return bookingService;
    }

    private static void mockUnitMethodsForInstance(BookingService bookingService, BookingDto bookingDto) {
        Mockito.when(bookingService.save(bookingDto))
                .thenReturn(getSuitableInstance(bookingDto));
        Mockito.when(bookingService.update(bookingDto))
                .thenReturn(bookingDto);
        Mockito.when(bookingService.findById(bookingDto.getId()))
                .thenReturn(bookingDto);
        Mockito.when(bookingService.findAllByUser(bookingDto.getUser()))
                .thenReturn(Collections.singletonList(bookingDto));
        Mockito.when(bookingService.findAllByAccommodation(bookingDto.getAccommodation()))
                .thenReturn(Collections.singletonList(bookingDto));
    }

    /*When calling save method in Controller, Id must be null in order for Controller to call mocked Service,
* but Instance with not-null Id must be returned in order to build valid URI for redirection.
* This is just a poor solution to return either primary or secondary instance which got their Ids set on build */
    private static BookingDto getSuitableInstance(BookingDto bookingDto) {
        return bookingDto.getFinalPrice() == (ServiceMocker.buildPrimaryBookingDto().getFinalPrice()) ?
                ServiceMocker.buildPrimaryBookingDto() : ServiceMocker.buildSecondaryBookingDto();
    }
    
    @Before
    public void setUp() {
        requestedBooking = ServiceMocker.buildPrimaryBookingDto();
        anotherBooking = ServiceMocker.buildSecondaryBookingDto();
    }


    @Test
    public void validCreationTest() {
        BookingDto responseBooking = ServiceMocker.buildPrimaryBookingDto();
        requestedBooking.setId(null);

        mockedHttpTestTemplate(
                HttpMethod.POST,
                baseEndpoint,
                requestedBooking,
                responseBooking,
                HttpStatus.CREATED,
                baseEndpoint + "/" + responseBooking.getId(),
                null
        );
    }

    @Test
    public void invalidCreationTest() {
        mockedHttpTestTemplate(
                HttpMethod.POST,
                baseEndpoint,
                requestedBooking,
                null,
                HttpStatus.BAD_REQUEST,
                null,
                "{resource.id_set}"
        );
    }

    @Test
    public void validEndpointUpdateTest() {
        Integer pathVariable = requestedBooking.getId();

        mockedHttpTestTemplate(
                HttpMethod.PUT,
                baseEndpoint + "/" + pathVariable,
                requestedBooking,
                requestedBooking,
                HttpStatus.OK,
                null,
                null
        );
    }

    @Test
    public void invalidEndpointUpdateTest() {
        Integer pathVariable = requestedBooking.getId() + 10;

        mockedHttpTestTemplate(
                HttpMethod.PUT,
                baseEndpoint + "/" + pathVariable,
                requestedBooking,
                null,
                HttpStatus.BAD_REQUEST,
                null,
                "{resource.id_not_consistent}"
        );
    }

    @Test
    public void validFetchBookingTest() {
        Integer pathVariable = requestedBooking.getId();

        mockedHttpTestTemplate(
                HttpMethod.GET,
                baseEndpoint + "/" + pathVariable,
                null,
                requestedBooking,
                HttpStatus.OK,
                null,
                null
        );
    }

    @Test
    public void invalidFetchBookingTest() {
        Integer pathVariable = requestedBooking.getId() + 10;

        mockedHttpTestTemplate(
                HttpMethod.GET,
                baseEndpoint + "/" + pathVariable,
                null,
                null,
                HttpStatus.NOT_FOUND,
                null,
                null
        );
    }

    @Test
    public void validFindAllBookingsTest() {
        mockedHttpTestTemplate(
                HttpMethod.GET,
                baseEndpoint,
                null,
                Lists.newArrayList(requestedBooking, anotherBooking),
                HttpStatus.OK,
                null,
                null
        );
    }

    @Test
    public void validDeleteBookingTest() {
        Integer pathVariable = requestedBooking.getId();

        mockedHttpTestTemplate(
                HttpMethod.DELETE,
                baseEndpoint + "/" + pathVariable,
                null,
                null,
                HttpStatus.NO_CONTENT,
                null,
                null
        );
    }

    @Test
    public void invalidDeleteBookingTest() {
        Integer pathVariable = requestedBooking.getId() + 10;

        mockedHttpTestTemplate(
                HttpMethod.DELETE,
                baseEndpoint + "/" + pathVariable,
                requestedBooking,
                null,
                HttpStatus.NOT_FOUND,
                null,
                null
        );
    }
}
