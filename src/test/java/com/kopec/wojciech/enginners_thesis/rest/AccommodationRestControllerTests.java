package com.kopec.wojciech.enginners_thesis.rest;

import com.kopec.wojciech.enginners_thesis.dto.AccommodationDto;
import com.kopec.wojciech.enginners_thesis.model.ModelProvider;
import com.kopec.wojciech.enginners_thesis.service.AccommodationService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AccommodationRestController.class, secure = false)
public class AccommodationRestControllerTests extends AbstractRestUnitTests {
    
    @MockBean
    private AccommodationService accommodationService;

    @InjectMocks
    private AccommodationRestController accommodationRestController;

    private String baseEndpoint = AccommodationRestController.class.getAnnotation(RequestMapping.class).value()[0];
    private AccommodationDto requestedAccommodation;
    private AccommodationDto existingAccommodation;


    public void mockServices() {
        Mockito.when(accommodationService.save(requestedAccommodation)).thenReturn(requestedAccommodation);
        Mockito.when(accommodationService.update(requestedAccommodation)).thenReturn(requestedAccommodation);
        /*Mockito.when(accommodationService.findByAccommodationname(requestedAccommodation.getAccommodationname()))
                .thenReturn(requestedAccommodation);
        Mockito.when(accommodationService.findByAccommodationnameContaining(requestedAccommodation.getAccommodationname()))
                .thenReturn(Collections
                .singletonList(requestedAccommodation));
        Mockito.when(accommodationService.findAll()).thenReturn(Arrays.asList(requestedAccommodation, existingAccommodation));
        */Mockito.when(accommodationService.findById(requestedAccommodation.getId())).thenReturn(requestedAccommodation);
        accommodationRestController = new AccommodationRestController(accommodationService);
    }

    @Before
    //Resets objects to their original state
    public void setUpBaseObject() {
        requestedAccommodation = AccommodationDto.toDto(ModelProvider.createAccomodation_1(ModelProvider.createUser_1()));
        requestedAccommodation.setId(10);
        //2nd Accommodation used for List assertions
        existingAccommodation = AccommodationDto.toDto(ModelProvider.createAccomodation_2(ModelProvider.createUser_2()));
        existingAccommodation.setId(20);
    }
}
