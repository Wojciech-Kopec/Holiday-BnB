package com.kopec.wojciech.enginners_thesis.rest;

import com.google.common.collect.Lists;
import com.kopec.wojciech.enginners_thesis.dto.AccommodationDto;
import com.kopec.wojciech.enginners_thesis.dto.BookingDto;
import com.kopec.wojciech.enginners_thesis.dto.UserDto;
import com.kopec.wojciech.enginners_thesis.service.BookingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;

import static com.kopec.wojciech.enginners_thesis.model.ModelProvider.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = BookingRestController.class, secure = false)
public class BookingRestControllerTests extends AbstractRestTest {

    @MockBean
    private BookingService bookingService;

    @InjectMocks
    private BookingRestController bookingRestController;

    private static String baseEndpoint = BookingRestController.class.getAnnotation(RequestMapping.class).value()[0];
    private static BookingDto requestedBooking;
    private static BookingDto existingBooking;


    public void mockServices() {
        bookingService = mockService(bookingService, requestedBooking, existingBooking);
        bookingRestController = new BookingRestController(bookingService);
    }

    public static BookingService mockService(BookingService bookingService, BookingDto requestedBooking, BookingDto
            existingBooking) {
        Mockito.when(bookingService.save(requestedBooking))
                .thenReturn(requestedBooking);
        Mockito.when(bookingService.update(requestedBooking))
                .thenReturn(requestedBooking);
        Mockito.when(bookingService.findAll())
                .thenReturn(Lists.newArrayList(requestedBooking, existingBooking));
        Mockito.when(bookingService.findById(existingBooking.getId()))
                .thenReturn(existingBooking);
        Mockito.when(bookingService.findById(requestedBooking.getId()))
                .thenReturn(requestedBooking);

//        WORKS!!!
//        Mockito.doReturn(Collections.singletonList(requestedBooking)).when(bookingService).findAllByUser(ArgumentMatchers.any(UserDto.class));
//        Mockito.doReturn(Collections.singletonList(requestedBooking)).when(bookingService).findAllByAccommodation(ArgumentMatchers.any(AccommodationDto.class));

        System.out.println(">>>>> BookingMock: AccommodationID=" + requestedBooking.getAccommodation().getId());
        System.out.println(">>>>> BookingMock: Accommodation=" + requestedBooking.getAccommodation());
        Mockito.doReturn(Collections.singletonList(requestedBooking)).when(bookingService).findAllByAccommodation(requestedBooking.getAccommodation());
        System.out.println(">>>>> BookingMock: UserID=" + requestedBooking.getUser().getId());
        Mockito.doReturn(Collections.singletonList(requestedBooking)).when(bookingService).findAllByUser(requestedBooking.getUser());


//        Mockito.when(bookingService.findAllByUser(requestedBooking.getUser()))
//                .thenReturn(Collections.singletonList(requestedBooking));
//        Mockito.when(bookingService.findAllByUser(existingBooking.getUser()))
//                .thenReturn(Collections.singletonList(existingBooking));
//        Mockito.when(bookingService.findAllByAccommodation(requestedBooking.getAccommodation()))
//                .thenReturn(Collections.singletonList(requestedBooking));
//        Mockito.when(bookingService.findAllByAccommodation(existingBooking.getAccommodation()))
//                .thenReturn(Collections.singletonList(existingBooking));

        return bookingService;
    }

    public static BookingDto getRequestedBooking() {
        return requestedBooking;
    }

    public static BookingDto getExistingBooking() {
        return existingBooking;
    }

    @Before
    //Resets objects to their original state
    public void setUp() {
        setUpDTOs();
    }

    public static void setUpDTOs() {
        requestedBooking = BookingDto.toDto(
                createBooking_1(createUser_2(), createAccommodation_1(createUser_1())));
        requestedBooking.setId(10);
        //2nd Booking used for List assertions
        existingBooking = BookingDto.toDto(
                createBooking_2(createUser_1(), createAccommodation_2(createUser_2())));
        existingBooking.setId(20);
    }

    @Test
    public void validCreationTest() {
        requestedBooking.setId(null);

        mockedHttpTestTemplate(
                HttpMethod.POST,
                baseEndpoint,
                requestedBooking,
                requestedBooking,
                HttpStatus.CREATED,
                baseEndpoint,
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
    public void validEndpointUpdateUserTest() {
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
    public void invalidEndpointUpdateUserTest() {
        Integer pathVariable = requestedBooking.getId() + 1;

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
                HttpStatus.FOUND,
                null,
                null
        );
    }

    @Test
    public void invalidFetchBookingTest() {
        Integer pathVariable = requestedBooking.getId() + 1;

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
                Lists.newArrayList(requestedBooking, existingBooking),
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
        Integer pathVariable = requestedBooking.getId() + 1;

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
