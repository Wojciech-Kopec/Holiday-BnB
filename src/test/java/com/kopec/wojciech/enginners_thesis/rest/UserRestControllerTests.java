package com.kopec.wojciech.enginners_thesis.rest;

import com.google.common.collect.Lists;
import com.kopec.wojciech.enginners_thesis.dto.AccommodationDto;
import com.kopec.wojciech.enginners_thesis.dto.BookingDto;
import com.kopec.wojciech.enginners_thesis.dto.UserDto;
import com.kopec.wojciech.enginners_thesis.service.AccommodationService;
import com.kopec.wojciech.enginners_thesis.service.BookingService;
import com.kopec.wojciech.enginners_thesis.service.UserService;

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
    private UserDto requestedUser;
    private UserDto anotherUser;


    public void mockServices() {
        userService = mockService(userService, requestedUser, anotherUser);
        accommodationService = ServiceMocker.mockAccommodationService(accommodationService);
        bookingService = ServiceMocker.mockBookingService(bookingService);

        userRestController = new UserRestController(userService, bookingService, accommodationService);
    }

    public static UserService mockService(UserService userService, UserDto primaryUser, UserDto secondaryUser) {
        mockUnitMethodsForInstance(userService, primaryUser);
        mockUnitMethodsForInstance(userService, secondaryUser);

        Mockito.when(userService.findAll())
                .thenReturn(Arrays.asList(primaryUser, secondaryUser));
        return userService;
    }

    private static void mockUnitMethodsForInstance(UserService userService, UserDto requestedUser) {
        Mockito.when(userService.save(requestedUser))
                .thenReturn(getSuitableInstance(requestedUser));
        Mockito.when(userService.update(requestedUser))
                .thenReturn(requestedUser);
        Mockito.when(userService.findByUsername(requestedUser.getUsername()))
                .thenReturn(requestedUser);
        Mockito.when(userService.findByUsernameContaining(requestedUser.getUsername()))
                .thenReturn(Collections
                .singletonList(requestedUser));
        Mockito.when(userService.findById(requestedUser.getId()))
                .thenReturn(requestedUser);
    }

    private static UserDto getSuitableInstance(UserDto userDto) {
        return userDto.getUsername().equals(ServiceMocker.buildPrimaryUserDto().getUsername()) ?
                ServiceMocker.buildPrimaryUserDto() : ServiceMocker.buildSecondaryUserDto();
    }

    @Before
    //Resets objects to their original state
    public void setUp() {
        requestedUser = ServiceMocker.buildPrimaryUserDto();
        anotherUser = ServiceMocker.buildSecondaryUserDto();
    }

    @Test
    public void validRegistrationTest() throws Exception {
        UserDto responseUser = ServiceMocker.buildPrimaryUserDto();
        requestedUser.setId(null);

        mockedHttpTestTemplate(
                HttpMethod.POST,
                baseEndpoint,
                requestedUser,
                responseUser,
                HttpStatus.CREATED,
                baseEndpoint + "/" + responseUser.getId(),
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
        Integer pathVariable = requestedUser.getId() + 10;

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
        Integer pathVariable = requestedUser.getId() + 10;

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
                Lists.newArrayList(requestedUser, anotherUser),
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
        Integer pathVariable = requestedUser.getId() + 10;

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
        BookingDto bookingDto = ServiceMocker.buildSecondaryBookingDto();
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
        Integer pathVariable = requestedUser.getId() + 10;

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
        AccommodationDto accommodationDto = ServiceMocker.buildPrimaryAccommodationDto();
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
        Integer pathVariable = requestedUser.getId() + 10;

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
