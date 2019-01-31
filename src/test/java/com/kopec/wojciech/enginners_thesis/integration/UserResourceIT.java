package com.kopec.wojciech.enginners_thesis.integration;

import com.google.common.collect.Lists;
import com.kopec.wojciech.enginners_thesis.dto.AccommodationDto;
import com.kopec.wojciech.enginners_thesis.dto.BookingDto;
import com.kopec.wojciech.enginners_thesis.dto.UserDto;
import com.kopec.wojciech.enginners_thesis.repository.UserRepository;
import com.kopec.wojciech.enginners_thesis.rest.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(secure = false, addFilters = false)
@TestPropertySource(locations = "classpath:application.properties")
public class UserResourceIT extends AbstractRestTest {

    @Autowired
    private UserRepository userRepository;

    private static String baseEndpoint = UserRestController.class.getAnnotation(RequestMapping.class).value()[0];
    private UserDto requestedUser;
    private UserDto anotherUser;

    @Before
    public void setUp() {
        requestedUser = ServiceMocker.buildPrimaryUserDto();
        requestedUser.setId(null);
        requestedUser = UserDto.toDto(userRepository.save(UserDto.toEntity(requestedUser)));

        anotherUser = ServiceMocker.buildSecondaryUserDto();
        anotherUser.setId(null);
        anotherUser = UserDto.toDto(userRepository.save(UserDto.toEntity(anotherUser)));

        assertThat(userRepository.count(), is(2L));
    }

    @Test
    public void validRegistrationTest() throws Exception {
        userRepository.deleteAll();
        UserDto responseUser = ServiceMocker.buildPrimaryUserDto();
        requestedUser.setId(null);

        MockHttpServletResponse response = performRequest(
                HttpMethod.POST,
                baseEndpoint,
                requestedUser
        );
        Integer createdId = userRepository.findByUsername(responseUser.getUsername()).getId();
        responseUser.setId(createdId);

        thenAssert(response,
                HttpStatus.CREATED,
                mapper.writeValueAsString(requestedUser),
                baseEndpoint + "/" + createdId,
                null
        );
        assertThat(userRepository.count(), is(1L));
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
        requestedUser.setUsername("UpdatesUsername1");
        Integer pathVariable = requestedUser.getId();

        httpTestTemplate(
                HttpMethod.PUT,
                baseEndpoint + "/" + pathVariable,
                requestedUser,
                requestedUser,
                HttpStatus.OK,
                null,
                null
        );
        assertThat(userRepository.findById(requestedUser.getId()), is(requestedUser));
    }

    @Test
    public void invalidEndpointUpdateUserTest() throws Exception {
        Integer pathVariable = requestedUser.getId() + 10;

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
        Integer pathVariable = requestedUser.getId() + 10;

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
                Lists.newArrayList(requestedUser, anotherUser),
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
        assertThat(userRepository.count(), is(1L));
    }

    @Test
    public void invalidDeleteUserTest() throws Exception {
        Integer pathVariable = requestedUser.getId() + 10;

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
        BookingDto bookingDto = ServiceMocker.buildSecondaryBookingDto();
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
        Integer pathVariable = requestedUser.getId() + 10;

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
        AccommodationDto accommodationDto = ServiceMocker.buildPrimaryAccommodationDto();
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
        Integer pathVariable = requestedUser.getId() + 10;

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

    @After
    public void tearDown() {
        userRepository.deleteAll();
    }
}
