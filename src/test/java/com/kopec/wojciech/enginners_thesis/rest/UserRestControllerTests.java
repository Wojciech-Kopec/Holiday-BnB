package com.kopec.wojciech.enginners_thesis.rest;

import com.google.common.collect.Lists;
import com.kopec.wojciech.enginners_thesis.dto.AccommodationDto;
import com.kopec.wojciech.enginners_thesis.dto.BookingDto;
import com.kopec.wojciech.enginners_thesis.dto.UserDto;
import com.kopec.wojciech.enginners_thesis.model.ModelProvider;
import com.kopec.wojciech.enginners_thesis.service.AccommodationService;
import com.kopec.wojciech.enginners_thesis.service.BookingService;
import com.kopec.wojciech.enginners_thesis.service.UserService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.Collections;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserRestController.class, secure = false)
public class UserRestControllerTests extends AbstractRestTest {

    @MockBean
    private UserService userService;

    @MockBean
    private BookingService bookingService;

    @MockBean
    private AccommodationService accommodationService;

    @InjectMocks
    private UserRestController userRestController;

    private static String baseEndpoint = UserRestController.class.getAnnotation(RequestMapping.class).value()[0];
    private static UserDto requestedUser;
    private static UserDto existingUser;

    private static BookingDto bookingDto;
    private static AccommodationDto accommodationDto;


    public void mockServices() {
        userService = mockService(userService, requestedUser, existingUser);
        accommodationService = ServiceMocker.mockAccommodationService(accommodationService);
        bookingService = ServiceMocker.mockBookingService(bookingService);

        accommodationDto = AccommodationRestControllerTests.getRequestedAccommodation();
        bookingDto = BookingRestControllerTests.getExistingBooking();

        userRestController = new UserRestController(userService, bookingService, accommodationService);
    }

    public static UserService mockService(UserService userService, UserDto requestedUser, UserDto existingUser) {
        Mockito.when(userService.save(requestedUser))
                .thenReturn(requestedUser);
        Mockito.when(userService.update(requestedUser))
                .thenReturn(requestedUser);
        Mockito.when(userService.findByUsername(requestedUser.getUsername()))
                .thenReturn(requestedUser);
        Mockito.when(userService.findByUsernameContaining(requestedUser.getUsername()))
                .thenReturn(Collections
                .singletonList(requestedUser));
        Mockito.when(userService.findAll())
                .thenReturn(Arrays.asList(requestedUser, existingUser));
        Mockito.when(userService.findById(ArgumentMatchers.anyInt()))
                .thenReturn(null);
        Mockito.when(userService.findById(requestedUser.getId()))
                .thenReturn(requestedUser);
        return userService;
    }

    @Before
    //Resets objects to their original state
    public void setUpBaseObject() {
        requestedUser = UserDto.toDto(ModelProvider.createUser_1());
        requestedUser.setId(10);
        //2nd User used for List assertions
        existingUser = UserDto.toDto(ModelProvider.createUser_2());
        existingUser.setId(20);
    }

    @Test
    public void validRegistrationTest() throws Exception {
        requestedUser.setId(null);

        mockedHttpTestTemplate(
                HttpMethod.POST,
                baseEndpoint,
                requestedUser,
                requestedUser,
                HttpStatus.CREATED,
                baseEndpoint,
                null
        );
    }

    @Test
    public void invalidRegistrationTest() throws Exception {
        mockedHttpTestTemplate(
                HttpMethod.POST,
                baseEndpoint,
                requestedUser,
                null,
                HttpStatus.BAD_REQUEST,
                null,
                "{resource.id_set}"
        );
    }

    @Test
    public void validEndpointUpdateUserTest() throws Exception {
        Integer pathVariable = requestedUser.getId();

        mockedHttpTestTemplate(
                HttpMethod.PUT,
                baseEndpoint + "/" + pathVariable,
                requestedUser,
                requestedUser,
                HttpStatus.OK,
                null,
                null
        );
    }

    @Test
    public void invalidEndpointUpdateUserTest() throws Exception {
        Integer pathVariable = requestedUser.getId() + 1;

        mockedHttpTestTemplate(
                HttpMethod.PUT,
                baseEndpoint + "/" + pathVariable,
                requestedUser,
                null,
                HttpStatus.BAD_REQUEST,
                null,
                "{resource.id_not_consistent}"
        );
    }

    @Test
    public void validFetchUserTest() throws Exception {
        Integer pathVariable = requestedUser.getId();

        mockedHttpTestTemplate(
                HttpMethod.GET,
                baseEndpoint + "/" + pathVariable,
                null,
                requestedUser,
                HttpStatus.FOUND,
                null,
                null
        );
    }

    @Test
    public void invalidFetchUserTest() throws Exception {
        Integer pathVariable = requestedUser.getId() + 1;

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
    public void validFindAllUsersNoParamTest() throws Exception {
        mockedHttpTestTemplate(
                HttpMethod.GET,
                baseEndpoint,
                null,
                Lists.newArrayList(requestedUser, existingUser),
                HttpStatus.OK,
                null,
                null
        );
    }

    @Test
    public void validFindAllUsersWithParamTest() throws Exception {
        mockedHttpTestTemplate(
                HttpMethod.GET,
                baseEndpoint + "?username=" + requestedUser.getUsername(),
                null,
                Lists.newArrayList(requestedUser),
                HttpStatus.OK,
                null,
                null
        );
    }

    @Test
    public void validDeleteUserTest() throws Exception {
        Integer pathVariable = requestedUser.getId();

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
    public void invalidDeleteUserTest() throws Exception {
        Integer pathVariable = requestedUser.getId() + 1;

        mockedHttpTestTemplate(
                HttpMethod.DELETE,
                baseEndpoint + "/" + pathVariable,
                requestedUser,
                null,
                HttpStatus.NOT_FOUND,
                null,
                null
        );
    }

    @Test
    public void validFetchBookings() {
        Integer pathVariable = requestedUser.getId();

        mockedHttpTestTemplate(
                HttpMethod.GET,
                baseEndpoint + "/" + pathVariable + "/bookings",
                null,
                Lists.newArrayList(bookingDto),
                HttpStatus.FOUND,
                null,
                null
        );
    }

    @Test
    public void invalidFetchBookings() {
        Integer pathVariable = requestedUser.getId() + 1;

        mockedHttpTestTemplate(
                HttpMethod.GET,
                baseEndpoint + "/" + pathVariable + "/bookings",
                null,
                null,
                HttpStatus.NOT_FOUND,
                null,
                null
        );
    }

    @Test
    public void validFetchAccommodations() {
        Integer pathVariable = requestedUser.getId();

        mockedHttpTestTemplate(
                HttpMethod.GET,
                baseEndpoint + "/" + pathVariable + "/accommodations",
                null,
                Lists.newArrayList(accommodationDto),
                HttpStatus.FOUND,
                null,
                null
        );
    }

    @Test
    public void invalidFetchAccommodations() {
        Integer pathVariable = requestedUser.getId() + 1;

        mockedHttpTestTemplate(
                HttpMethod.GET,
                baseEndpoint + "/" + pathVariable + "/accommodations",
                null,
                null,
                HttpStatus.NOT_FOUND,
                null,
                null
        );
    }
}
