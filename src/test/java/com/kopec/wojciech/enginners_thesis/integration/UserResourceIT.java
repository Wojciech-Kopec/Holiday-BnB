package com.kopec.wojciech.enginners_thesis.integration;

import com.google.common.collect.Lists;
import com.kopec.wojciech.enginners_thesis.dto.AccommodationDto;
import com.kopec.wojciech.enginners_thesis.dto.BookingDto;
import com.kopec.wojciech.enginners_thesis.dto.UserDto;
import com.kopec.wojciech.enginners_thesis.model.ModelProvider;
import com.kopec.wojciech.enginners_thesis.model.User;
import com.kopec.wojciech.enginners_thesis.repository.UserRepository;
import com.kopec.wojciech.enginners_thesis.rest.*;
import com.kopec.wojciech.enginners_thesis.service.AccommodationService;
import com.kopec.wojciech.enginners_thesis.service.BookingService;
import com.kopec.wojciech.enginners_thesis.service.UserService;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.junit.jupiter.api.Test;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(secure = false, addFilters = false)
@TestPropertySource(locations = "classpath:application.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserResourceIT extends AbstractRestTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private AccommodationService accommodationService;

    @Autowired
    private UserRestController userRestController;

    @Autowired
    private MockMvc mockMvc;


    private static String baseEndpoint = UserRestController.class.getAnnotation(RequestMapping.class).value()[0];
    private static UserDto requestedUser;
    private static UserDto existingUser;

    private static BookingDto bookingDto;
    private static AccommodationDto accommodationDto;


    public void mockServices() {
    }

    @Before
    public void setUpBaseObject() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userRestController).build();
        //Basically User object with all NULL properties
        requestedUser = UserDto.toDto(ModelProvider.createUser_1());
        requestedUser.setId(10);
    }

    @Test
    public void validRegistrationTest() throws Exception {
        requestedUser.setId(null);

        httpTestTemplate(
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
        httpTestTemplate(
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
    //TODO modifications to User to check if update is performed
        httpTestTemplate(
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

        httpTestTemplate(
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

        httpTestTemplate(
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

        httpTestTemplate(
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
        httpTestTemplate(
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
        httpTestTemplate(
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

        httpTestTemplate(
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

        httpTestTemplate(
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

        httpTestTemplate(
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

        httpTestTemplate(
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

        httpTestTemplate(
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

        httpTestTemplate(
                HttpMethod.GET,
                baseEndpoint + "/" + pathVariable + "/accommodations",
                null,
                null,
                HttpStatus.NOT_FOUND,
                null,
                null
        );
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("----- TESTS HAVE ENDED -----");
        System.out.println("----- TESTS HAVE ENDED -----");
        System.out.println("----- TESTS HAVE ENDED -----");
    }
}
