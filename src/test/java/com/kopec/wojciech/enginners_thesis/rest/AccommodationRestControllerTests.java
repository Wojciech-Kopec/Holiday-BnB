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

import static com.kopec.wojciech.enginners_thesis.model.ModelProvider.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AccommodationRestController.class, secure = false)
public class AccommodationRestControllerTests extends AbstractRestTest {

    @MockBean
    private AccommodationService accommodationService;

    @MockBean
    private BookingService bookingService;

    @InjectMocks
    private AccommodationRestController accommodationRestController;

    private static String baseEndpoint = AccommodationRestController.class.getAnnotation(RequestMapping.class).value()[0];
    private static AccommodationDto requestedAccommodation;
    private static AccommodationDto existingAccommodation;
    private static AccommodationCriteria criteria;
    private static BookingDto bookingDto;


    public void mockServices() {
        accommodationService = mockService(accommodationService, requestedAccommodation, existingAccommodation);
        bookingService = ServiceMocker.mockBookingService(bookingService);
        bookingDto = BookingRestControllerTests.getRequestedBooking();
        accommodationRestController = new AccommodationRestController(accommodationService, bookingService);

    }

    public static AccommodationService mockService(AccommodationService accommodationService,
                                                   AccommodationDto requestedAccommodation,
                                                   AccommodationDto existingAccommodation) {
        Mockito.when(accommodationService.save(requestedAccommodation))
                .thenReturn(requestedAccommodation);
        Mockito.when(accommodationService.update(requestedAccommodation))
                .thenReturn(requestedAccommodation);
        Mockito.when(accommodationService.findAll(ArgumentMatchers.any(AccommodationCriteria.class)))
                .thenReturn(Collections.singletonList(requestedAccommodation));
        Mockito.when(accommodationService.findAll(ArgumentMatchers.eq(AccommodationCriteria.builder().build())))
                .thenReturn(Lists.newArrayList(requestedAccommodation, existingAccommodation));
        Mockito.when(accommodationService.findById(requestedAccommodation.getId()))
                .thenReturn(requestedAccommodation);

        //  fixme WHICH ONE?
        Mockito.when(accommodationService.findAllByUser(requestedAccommodation.getUser()))
                .thenReturn(Collections.singletonList(requestedAccommodation));
        Mockito.doReturn(Collections.singletonList(requestedAccommodation)).when(accommodationService).findAllByUser(requestedAccommodation.getUser());

    return accommodationService;
    }

    @Before
    //Resets objects to their original state
    public void setUp() {
        setUpDTOs();
    }

    public static void setUpDTOs() {
        requestedAccommodation = AccommodationDto.toDto(
                createAccommodation_1(createUser_1()));
        requestedAccommodation.setId(10);
        //2nd Accommodation used for List assertions
        existingAccommodation = AccommodationDto.toDto(
                createAccommodation_2(createUser_2()));
        existingAccommodation.setId(20);

        criteria = buildCriteria(requestedAccommodation);
    }

    @Test
    public void validCreationTest() {
        requestedAccommodation.setId(null);

        mockedHttpTestTemplate(
                HttpMethod.POST,
                baseEndpoint,
                requestedAccommodation,
                requestedAccommodation,
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
                requestedAccommodation,
                null,
                HttpStatus.BAD_REQUEST,
                null,
                "{resource.id_set}"
        );
    }

    @Test
    public void validEndpointUpdateUserTest() {
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
    public void invalidEndpointUpdateUserTest() {
        Integer pathVariable = requestedAccommodation.getId() + 1;

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
                HttpStatus.FOUND,
                null,
                null
        );
    }

    @Test
    public void invalidFetchAccommodationTest() {
        Integer pathVariable = requestedAccommodation.getId() + 1;

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
                Lists.newArrayList(requestedAccommodation, existingAccommodation),
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
                baseEndpoint + addCriteriaAsRequestParams(criteria),
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
                Lists.newArrayList(requestedAccommodation, existingAccommodation),
                HttpStatus.OK,
                null,
                null
        );
    }

    @Test
    public void validFindAllAccommodationsWithCriteriaNullLocalizationTest() {
        criteria.setLocalization(null);

        mockedHttpTestTemplate(
                HttpMethod.GET,
                baseEndpoint + addCriteriaAsRequestParams(criteria),
                null,
                Lists.newArrayList(requestedAccommodation),
                HttpStatus.OK,
                null,
                null
        );
    }

    @Test
    public void validFindAllAccommodationsWithPartialCriteriaTest() {
        criteria.setName(null);
        criteria.setAmenities(null);
        criteria.setLocalization(null);
        criteria.setMinPricePerNight(null);

        mockedHttpTestTemplate(
                HttpMethod.GET,
                baseEndpoint + addCriteriaAsRequestParams(criteria),
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
                Lists.newArrayList(requestedAccommodation, existingAccommodation),
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
        Integer pathVariable = requestedAccommodation.getId() + 1;

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
        Integer pathVariable = requestedAccommodation.getId();

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
        Integer pathVariable = requestedAccommodation.getId() + 1;

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

    private String addCriteriaAsRequestParams(AccommodationCriteria criteria) {
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
            //Localization criteria
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

    public static AccommodationDto getRequestedAccommodation() {
        return requestedAccommodation;
    }

    public static AccommodationDto getExistingAccommodation() {
        return existingAccommodation;
    }
}
