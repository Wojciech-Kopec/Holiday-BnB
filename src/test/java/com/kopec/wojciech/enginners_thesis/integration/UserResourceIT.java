package com.kopec.wojciech.enginners_thesis.integration;

import com.google.common.collect.Lists;
import com.kopec.wojciech.enginners_thesis.rest.*;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UserResourceIT extends AbstractRestIT {
    private static String baseEndpoint = UserRestController.class.getAnnotation(RequestMapping.class).value()[0];

    @Test
    public void validRegistrationTest() throws Exception {
        this.tearDown();
        primaryUserDto.setId(null);

        MockHttpServletResponse response = performRequest(
                HttpMethod.POST,
                baseEndpoint,
                primaryUserDto
        );
        Integer createdId = userRepository.findByUsername(primaryUserDto.getUsername()).getId();
        primaryUserDto.setId(createdId);

        thenAssert(response,
                HttpStatus.CREATED,
                mapper.writeValueAsString(primaryUserDto),
                baseEndpoint + "/" + createdId,
                null
        );
        assertThat(userRepository.count(), is(1L));
    }

    @Test
    public void invalidRegistrationTest() {
        httpTestTemplate(
                HttpMethod.POST,
                baseEndpoint,
                primaryUserDto,
                null,
                HttpStatus.BAD_REQUEST,
                null,
                "{resource.id_set}"
        );
    }

    @Test
    public void validEndpointUpdateTest() {
        primaryUserDto.setUsername("UpdatesUsername1");
        Integer pathVariable = primaryUserDto.getId();

        httpTestTemplate(
                HttpMethod.PUT,
                baseEndpoint + "/" + pathVariable,
                primaryUserDto,
                primaryUserDto,
                HttpStatus.OK,
                null,
                null
        );
        assertThat(userRepository.findById(primaryUserDto.getId()), is(primaryUserDto));
    }

    @Test
    public void invalidEndpointUpdateTest() {
        int pathVariable = primaryUserDto.getId() + 10;

        httpTestTemplate(
                HttpMethod.PUT,
                baseEndpoint + "/" + pathVariable,
                primaryUserDto,
                null,
                HttpStatus.BAD_REQUEST,
                null,
                "{resource.id_not_consistent}"
        );
    }

    @Test
    public void validFetchUserTest() {
        Integer pathVariable = primaryUserDto.getId();

        httpTestTemplate(
                HttpMethod.GET,
                baseEndpoint + "/" + pathVariable,
                null,
                primaryUserDto,
                HttpStatus.FOUND,
                null,
                null
        );
    }

    @Test
    public void invalidFetchUserTest() {
        int pathVariable = primaryUserDto.getId() + 10;

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
    public void validFindAllUsersNoParamTest() {
        httpTestTemplate(
                HttpMethod.GET,
                baseEndpoint,
                null,
                Lists.newArrayList(primaryUserDto, secondaryUserDto),
                HttpStatus.OK,
                null,
                null
        );
    }

    @Test
    public void validFindAllUsersWithParamTest() {
        httpTestTemplate(
                HttpMethod.GET,
                baseEndpoint + "?username=" + primaryUserDto.getUsername(),
                null,
                Lists.newArrayList(primaryUserDto),
                HttpStatus.OK,
                null,
                null
        );
    }

    @Test
    public void validDeleteUserTest() {
        Integer pathVariable = primaryUserDto.getId();

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
    public void invalidDeleteUserTest() {
        int pathVariable = primaryUserDto.getId() + 10;

        httpTestTemplate(
                HttpMethod.DELETE,
                baseEndpoint + "/" + pathVariable,
                primaryUserDto,
                null,
                HttpStatus.NOT_FOUND,
                null,
                null
        );
    }

    @Test
    public void validFetchBookings() {
        Integer pathVariable = primaryUserDto.getId();

        httpTestTemplate(
                HttpMethod.GET,
                baseEndpoint + "/" + pathVariable + "/bookings",
                null,
                Lists.newArrayList(secondaryBookingDto),
                HttpStatus.FOUND,
                null,
                null
        );
    }

    @Test
    public void invalidFetchBookings() {
        Integer pathVariable = primaryUserDto.getId() + 10;

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
        Integer pathVariable = primaryUserDto.getId();

        httpTestTemplate(
                HttpMethod.GET,
                baseEndpoint + "/" + pathVariable + "/accommodations",
                null,
                Lists.newArrayList(primaryAccommodationDto),
                HttpStatus.FOUND,
                null,
                null
        );
    }

    @Test
    public void invalidFetchAccommodations() {
        int pathVariable = primaryUserDto.getId() + 10;

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
}
