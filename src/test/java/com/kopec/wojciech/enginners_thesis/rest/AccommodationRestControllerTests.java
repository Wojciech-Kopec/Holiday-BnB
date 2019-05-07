package com.kopec.wojciech.enginners_thesis.rest;

import com.google.common.collect.Lists;
import com.kopec.wojciech.enginners_thesis.dto.AccommodationDto;
import com.kopec.wojciech.enginners_thesis.dto.BookingDto;
import com.kopec.wojciech.enginners_thesis.dto.LocalizationDto;
import com.kopec.wojciech.enginners_thesis.service.AccommodationService;
import com.kopec.wojciech.enginners_thesis.service.BookingService;
import com.kopec.wojciech.enginners_thesis.specification.AccommodationCriteria;
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
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AccommodationRestController.class, secure = false)
public class AccommodationRestControllerTests extends AbstractRestMockedTest {

    @MockBean
    private AccommodationService accommodationService;

    @MockBean
    private BookingService bookingService;

    @InjectMocks
    private AccommodationRestController accommodationRestController;

    private static String baseEndpoint = AccommodationRestController.class.getAnnotation(RequestMapping.class).value()[0];
    private AccommodationDto requestedAccommodation;
    private AccommodationDto anotherAccommodation;
    private AccommodationCriteria filteringCriteria;


    public void mockServices() {
        accommodationService = mockService(accommodationService, requestedAccommodation, anotherAccommodation);
        bookingService = ServiceMocker.mockBookingService(bookingService);

        accommodationRestController = new AccommodationRestController(accommodationService, bookingService);
    }

    public static AccommodationService mockService(AccommodationService accommodationService,
                                                   AccommodationDto primaryAccommodation,
                                                   AccommodationDto secondaryAccommodation) {

        mockUnitMethodsForInstance(accommodationService, primaryAccommodation);
        mockUnitMethodsForInstance(accommodationService, secondaryAccommodation);

        Mockito.when(accommodationService.findAll(ArgumentMatchers.any(AccommodationCriteria.class)))
                .thenReturn(Collections.singletonList(primaryAccommodation));
        Mockito.when(accommodationService.findAll(ArgumentMatchers.eq(AccommodationCriteria.builder().build())))
                //null values
                .thenReturn(Lists.newArrayList(primaryAccommodation, secondaryAccommodation));

        return accommodationService;
    }

    private static void mockUnitMethodsForInstance(AccommodationService accommodationService, AccommodationDto
            accommodationDto) {
        Mockito.when(accommodationService.save(accommodationDto))
                .thenReturn(getSuitableInstance(accommodationDto));
        Mockito.when(accommodationService.update(accommodationDto))
                .thenReturn(accommodationDto);
        Mockito.when(accommodationService.findById(accommodationDto.getId()))
                .thenReturn(accommodationDto);
        Mockito.when(accommodationService.findAllByUser(accommodationDto.getUser()))
                .thenReturn(Collections.singletonList(accommodationDto));
    }

    /*When calling save method in Controller, Id must be null in order for Controller to call mocked Service,
    * but Instance with not-null Id must be returned in order to build valid URI for redirection.
    * This is just a poor solution to return either primary or secondary instance which got their Ids set on build */
    private static AccommodationDto getSuitableInstance(AccommodationDto accommodationDto) {
        return accommodationDto.getName().equals(ServiceMocker.buildPrimaryAccommodationDto().getName()) ?
                ServiceMocker.buildPrimaryAccommodationDto() : ServiceMocker.buildSecondaryAccommodationDto();
    }

    @Before
    public void setUp() {
        requestedAccommodation = ServiceMocker.buildPrimaryAccommodationDto();
        anotherAccommodation = ServiceMocker.buildSecondaryAccommodationDto();
        filteringCriteria = buildCriteria(requestedAccommodation);
    }

    @Test
    public void validCreationTest() {
        AccommodationDto responseAccommodation = ServiceMocker.buildPrimaryAccommodationDto();
        requestedAccommodation.setId(null);

        mockedHttpTestTemplate(
                HttpMethod.POST,
                baseEndpoint,
                requestedAccommodation,
                responseAccommodation,
                HttpStatus.CREATED,
                baseEndpoint + "/" + responseAccommodation.getId(),
                null
        );
    }

    @Test
    public void invalidCreationTest() {
        mockedHttpTestTemplate(
                HttpMethod.POST,
                baseEndpoint,
                requestedAccommodation,
                null,
                HttpStatus.BAD_REQUEST,
                null,
                "{resource.id_set}"
        );
    }

    @Test
    public void validEndpointUpdateTest() {
        Integer pathVariable = requestedAccommodation.getId();

        mockedHttpTestTemplate(
                HttpMethod.PUT,
                baseEndpoint + "/" + pathVariable,
                requestedAccommodation,
                requestedAccommodation,
                HttpStatus.OK,
                null,
                null
        );
    }

    @Test
    public void invalidEndpointUpdateTest() {
        Integer pathVariable = requestedAccommodation.getId() + 10;

        mockedHttpTestTemplate(
                HttpMethod.PUT,
                baseEndpoint + "/" + pathVariable,
                requestedAccommodation,
                null,
                HttpStatus.BAD_REQUEST,
                null,
                "{resource.id_not_consistent}"
        );
    }

    @Test
    public void validFetchAccommodationTest() {
        Integer pathVariable = requestedAccommodation.getId();

        mockedHttpTestTemplate(
                HttpMethod.GET,
                baseEndpoint + "/" + pathVariable,
                null,
                requestedAccommodation,
                HttpStatus.OK,
                null,
                null
        );
    }

    @Test
    public void invalidFetchAccommodationTest() {
        Integer pathVariable = requestedAccommodation.getId() + 10;

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
    public void validFindAllAccommodationsNoCriteriaTest() {
        mockedHttpTestTemplate(
                HttpMethod.GET,
                baseEndpoint,
                null,
                Lists.newArrayList(requestedAccommodation, anotherAccommodation),
                HttpStatus.OK,
                null,
                null
        );
    }

    @Test
    //FIXME
    public void validFindAllAccommodationsWithCriteriaTest() {
        mockedHttpTestTemplate(
                HttpMethod.GET,
                baseEndpoint + addCriteriaAsRequestParams(filteringCriteria),
                null,
                Lists.newArrayList(requestedAccommodation),
                HttpStatus.OK,
                null,
                null
        );
    }

    @Test
    public void validFindAllAccommodationsWithCriteriaNullValuesTest() {
        mockedHttpTestTemplate(
                HttpMethod.GET,
                baseEndpoint + addCriteriaAsRequestParams(AccommodationCriteria.builder().build()),
                null,
                Lists.newArrayList(requestedAccommodation, anotherAccommodation),
                HttpStatus.OK,
                null,
                null
        );
    }

    @Test
    public void validFindAllAccommodationsWithCriteriaNullLocalizationTest() {
        filteringCriteria.setLocalization(null);

        mockedHttpTestTemplate(
                HttpMethod.GET,
                baseEndpoint + addCriteriaAsRequestParams(filteringCriteria),
                null,
                Lists.newArrayList(requestedAccommodation),
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

        mockedHttpTestTemplate(
                HttpMethod.GET,
                baseEndpoint + addCriteriaAsRequestParams(filteringCriteria),
                null,
                Lists.newArrayList(requestedAccommodation),
                HttpStatus.OK,
                null,
                null
        );
    }

    @Test
    public void validFindAllAccommodationsWithNullCriteriaTest() {
        mockedHttpTestTemplate(
                HttpMethod.GET,
                baseEndpoint + addCriteriaAsRequestParams(null),
                null,
                Lists.newArrayList(requestedAccommodation, anotherAccommodation),
                HttpStatus.OK,
                null,
                null
        );
    }

    @Test
    public void validDeleteAccommodationTest() {
        Integer pathVariable = requestedAccommodation.getId();

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
    public void invalidDeleteAccommodationTest() {
        Integer pathVariable = requestedAccommodation.getId() + 10;

        mockedHttpTestTemplate(
                HttpMethod.DELETE,
                baseEndpoint + "/" + pathVariable,
                requestedAccommodation,
                null,
                HttpStatus.NOT_FOUND,
                null,
                null
        );
    }

    @Test
    public void validFetchBookings() {
        BookingDto bookingDto = ServiceMocker.buildPrimaryBookingDto();
        Integer pathVariable = requestedAccommodation.getId();

        mockedHttpTestTemplate(
                HttpMethod.GET,
                baseEndpoint + "/" + pathVariable + "/bookings",
                null,
                Lists.newArrayList(bookingDto),
                HttpStatus.OK,
                null,
                null
        );
    }

    @Test
    public void invalidFetchBookings() {
        Integer pathVariable = requestedAccommodation.getId() + 10;

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

    public static String addCriteriaAsRequestParams(AccommodationCriteria criteria) {
        StringBuilder sb = new StringBuilder("?");

        if (criteria != null) {
            if (criteria.getName() != null) {
                sb.append("name=").append(criteria.getName()).append("&");
            }
            if (criteria.getAccommodationTypes() != null && criteria.getAccommodationTypes().size() > 0) {
                criteria.getAccommodationTypes().forEach(type -> sb.append("accommodationType=").append(type.name())
                        .append("&"));
            }
            if (criteria.getRequiredGuestCount() != null) {
                sb.append("requiredGuestCount=").append(criteria.getRequiredGuestCount().toString()).append("&");
            }
            if (criteria.getMinPricePerNight() != null) {
                sb.append("minPricePerNight=").append(criteria.getMinPricePerNight().toString()).append("&");
            }
            if (criteria.getMaxPricePerNight() != null) {
                sb.append("maxPricePerNight=").append(criteria.getMaxPricePerNight().toString()).append("&");
            }
            if (criteria.getAmenities() != null && criteria.getAmenities().size() > 0) {
                criteria.getAmenities().forEach(amenity -> sb.append("amenity=").append(amenity).append("&"));
            }
            //Localization filteringCriteria
            if (criteria.getLocalization() != null) {
                LocalizationDto localization = criteria.getLocalization();
                if (localization.getCountry() != null) {
                    sb.append("country=").append(localization.getCountry()).append("&");
                }
                if (localization.getState() != null) {
                    sb.append("state=").append(localization.getState()).append("&");
                }
                if (localization.getCity() != null) {
                    sb.append("city=").append(localization.getCity()).append("&");
                }
                if (localization.getAddress() != null) {
                    sb.append("address=").append(localization.getAddress()).append("&");
                }
            }
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }
}
