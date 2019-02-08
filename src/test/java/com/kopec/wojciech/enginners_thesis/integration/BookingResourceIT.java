package com.kopec.wojciech.enginners_thesis.integration;

import com.google.common.collect.Lists;
import com.kopec.wojciech.enginners_thesis.dto.BookingDto;
import com.kopec.wojciech.enginners_thesis.rest.BookingRestController;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BookingResourceIT extends AbstractRestIT {

    private static String baseEndpoint = BookingRestController.class.getAnnotation(RequestMapping.class).value()[0];


    @Test
    public void validCreationTest() throws Exception {
        bookingRepository.deleteAll();
        primaryBookingDto.setId(null);

        MockHttpServletResponse response = performRequest(
                HttpMethod.POST,
                baseEndpoint,
                primaryBookingDto
        );
        primaryBookingDto = BookingDto.toDto(bookingRepository.findAll().get(0));

        thenAssert(response,
                HttpStatus.CREATED,
                mapper.writeValueAsString(primaryBookingDto),
                baseEndpoint + "/" + primaryBookingDto.getId(),
                null
        );
        assertThat(accommodationRepository.count(), is(1L));
    }

    @Test
    public void invalidCreationTest() {
        httpTestTemplate(
                HttpMethod.POST,
                baseEndpoint,
                primaryBookingDto,
                null,
                HttpStatus.BAD_REQUEST,
                null,
                "{resource.id_set}"
        );
    }

    @Test
    public void validEndpointUpdateTest() {
        Integer pathVariable = primaryBookingDto.getId();

        httpTestTemplate(
                HttpMethod.PUT,
                baseEndpoint + "/" + pathVariable,
                primaryBookingDto,
                primaryBookingDto,
                HttpStatus.OK,
                null,
                null
        );
    }

    @Test
    public void validFetchBookingTest() {
        Integer pathVariable = primaryBookingDto.getId();

        httpTestTemplate(
                HttpMethod.GET,
                baseEndpoint + "/" + pathVariable,
                null,
                primaryBookingDto,
                HttpStatus.FOUND,
                null,
                null
        );
    }

    @Test
    public void validFindAllBookingsTest() {
        httpTestTemplate(
                HttpMethod.GET,
                baseEndpoint,
                null,
                Lists.newArrayList(primaryBookingDto, secondaryBookingDto),
                HttpStatus.OK,
                null,
                null
        );
    }

    @Test
    public void validDeleteBookingTest() {
        Integer pathVariable = primaryBookingDto.getId();

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


}
