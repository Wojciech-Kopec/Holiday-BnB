package com.kopec.wojciech.enginners_thesis.integration;

import com.google.common.collect.Lists;
import com.kopec.wojciech.enginners_thesis.dto.AccommodationDto;
import com.kopec.wojciech.enginners_thesis.dto.LocalizationDto;
import com.kopec.wojciech.enginners_thesis.rest.AccommodationRestController;
import com.kopec.wojciech.enginners_thesis.rest.AccommodationRestControllerTests;
import com.kopec.wojciech.enginners_thesis.specification.AccommodationCriteria;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AccommodationResourceIT extends AbstractRestIT {

    private static String baseEndpoint = AccommodationRestController.class.getAnnotation(RequestMapping.class).value
            ()[0];
    private AccommodationCriteria filteringCriteria;

    @Before
    @Override
    public void setUp() {
        super.setUp();
        filteringCriteria = buildCriteria(primaryAccommodationDto);
    }

    @Test
    public void validCreationTest() throws Exception {
        accommodationRepository.deleteAll();
        localizationRepository.deleteAll();
        amenityRepository.deleteAll();

        primaryAccommodationDto.setId(null);
        primaryAccommodationDto.getLocalization().setId(null);
        primaryAccommodationDto.getAmenities().forEach(amenityDto -> amenityDto.setId(null));

        MockHttpServletResponse response = performRequest(
                HttpMethod.POST,
                baseEndpoint,
                primaryAccommodationDto
        );
        primaryAccommodationDto = AccommodationDto.toDto(accommodationRepository.findAll().get(0));

        thenAssert(response,
                HttpStatus.CREATED,
                mapper.writeValueAsString(primaryAccommodationDto),
                baseEndpoint + "/" + primaryAccommodationDto.getId(),
                null
        );
        assertThat(accommodationRepository.count(), is(1L));
        assertThat(localizationRepository.count(), is(1L));
        assertThat(amenityRepository.count(), is(2L));
    }

    @Test
    public void invalidCreationTest() {
        httpTestTemplate(
                HttpMethod.POST,
                baseEndpoint,
                primaryAccommodationDto,
                null,
                HttpStatus.BAD_REQUEST,
                null,
                "{resource.id_set}"
        );
    }

    @Test
    public void validEndpointUpdateTest() {
        Integer pathVariable = primaryAccommodationDto.getId();

        httpTestTemplate(
                HttpMethod.PUT,
                baseEndpoint + "/" + pathVariable,
                primaryAccommodationDto,
                primaryAccommodationDto,
                HttpStatus.OK,
                null,
                null
        );
    }

    @Test
    public void invalidEndpointUpdateTest() {
        int pathVariable = primaryAccommodationDto.getId() + 10;

        httpTestTemplate(
                HttpMethod.PUT,
                baseEndpoint + "/" + pathVariable,
                primaryAccommodationDto,
                null,
                HttpStatus.BAD_REQUEST,
                null,
                "{resource.id_not_consistent}"
        );
    }

    @Test
    public void validFetchAccommodationTest() {
        Integer pathVariable = primaryAccommodationDto.getId();

        httpTestTemplate(
                HttpMethod.GET,
                baseEndpoint + "/" + pathVariable,
                null,
                primaryAccommodationDto,
                HttpStatus.FOUND,
                null,
                null
        );
    }

    @Test
    public void invalidFetchAccommodationTest() {
        int pathVariable = primaryAccommodationDto.getId() + 10;

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
    public void validFindAllAccommodationsNoCriteriaTest() {
        httpTestTemplate(
                HttpMethod.GET,
                baseEndpoint,
                null,
                Lists.newArrayList(primaryAccommodationDto, secondaryAccommodationDto),
                HttpStatus.OK,
                null,
                null
        );
    }

    @Test
    //FIXME
    public void validFindAllAccommodationsWithCriteriaTest() {
        httpTestTemplate(
                HttpMethod.GET,
                baseEndpoint + addCriteriaAsRequestParams(filteringCriteria),
                null,
                Lists.newArrayList(primaryAccommodationDto),
                HttpStatus.OK,
                null,
                null
        );
    }

    @Test
    public void validFindAllAccommodationsWithCriteriaNullValuesTest() {
        httpTestTemplate(
                HttpMethod.GET,
                baseEndpoint + addCriteriaAsRequestParams(AccommodationCriteria.builder().build()),
                null,
                Lists.newArrayList(primaryAccommodationDto, secondaryAccommodationDto),
                HttpStatus.OK,
                null,
                null
        );
    }

    @Test
    public void validFindAllAccommodationsWithCriteriaNullLocalizationTest() {
        filteringCriteria.setLocalization(null);

        httpTestTemplate(
                HttpMethod.GET,
                baseEndpoint + addCriteriaAsRequestParams(filteringCriteria),
                null,
                Lists.newArrayList(primaryAccommodationDto),
                HttpStatus.OK,
                null,
                null
        );
    }

    @Test
    public void validFindAllAccommodationsWithPartialCriteriaTest() {
        filteringCriteria.setName(null);
        filteringCriteria.setAmenities(null);
        filteringCriteria.setLocalization(null);
        filteringCriteria.setMinPricePerNight(null);

        httpTestTemplate(
                HttpMethod.GET,
                baseEndpoint + addCriteriaAsRequestParams(filteringCriteria),
                null,
                Lists.newArrayList(primaryAccommodationDto),
                HttpStatus.OK,
                null,
                null
        );
    }

    @Test
    public void validFindAllAccommodationsWithNullCriteriaTest() {
        httpTestTemplate(
                HttpMethod.GET,
                baseEndpoint + addCriteriaAsRequestParams(null),
                null,
                Lists.newArrayList(primaryAccommodationDto, secondaryAccommodationDto),
                HttpStatus.OK,
                null,
                null
        );
    }

    @Test
    public void validDeleteAccommodationTest() {
        Integer pathVariable = primaryAccommodationDto.getId();

        httpTestTemplate(
                HttpMethod.DELETE,
                baseEndpoint + "/" + pathVariable,
                null,
                null,
                HttpStatus.NO_CONTENT,
                null,
                null
        );
        assertThat(accommodationRepository.count(), is(1L));
        assertThat(localizationRepository.count(), is(1L));
        assertThat(amenityRepository.count(), is(3L));
    }

    @Test
    public void invalidDeleteAccommodationTest() {
        int pathVariable = primaryAccommodationDto.getId() + 10;

        httpTestTemplate(
                HttpMethod.DELETE,
                baseEndpoint + "/" + pathVariable,
                primaryAccommodationDto,
                null,
                HttpStatus.NOT_FOUND,
                null,
                null
        );
    }

    @Test
    public void validFetchBookings() {
        Integer pathVariable = primaryAccommodationDto.getId();

        httpTestTemplate(
                HttpMethod.GET,
                baseEndpoint + "/" + pathVariable + "/bookings",
                null,
                Lists.newArrayList(primaryBookingDto),
                HttpStatus.FOUND,
                null,
                null
        );
    }

    @Test
    public void invalidFetchBookings() {
        int pathVariable = primaryAccommodationDto.getId() + 10;

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

    private static AccommodationCriteria buildCriteria(AccommodationDto accommodation) {
        return AccommodationCriteria.builder()
                .name(accommodation.getName())
                .accommodationTypes(Collections.singletonList(accommodation.getAccommodationType()))
                .requiredGuestCount(accommodation.getMaxGuests())
                .maxPricePerNight(accommodation.getPricePerNight())
                .amenities(accommodation.getAmenities().stream().map(
                        amenity -> amenity.getType().toString()).collect(Collectors.toList()))
                .localization(accommodation.getLocalization())
                .build();
    }

    private String addCriteriaAsRequestParams(AccommodationCriteria criteria) {
        return AccommodationRestControllerTests.addCriteriaAsRequestParams(criteria);
    }
}
