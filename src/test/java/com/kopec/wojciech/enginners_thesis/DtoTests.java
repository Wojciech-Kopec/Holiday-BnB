package com.kopec.wojciech.enginners_thesis;

import com.kopec.wojciech.enginners_thesis.dto.UserDto;
import com.kopec.wojciech.enginners_thesis.model.Accommodation;
import com.kopec.wojciech.enginners_thesis.model.Booking;
import com.kopec.wojciech.enginners_thesis.model.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.kopec.wojciech.enginners_thesis.EnginnersThesisApplication.*;

public class DtoTests {
    private static Logger logger = LoggerFactory.getLogger(DtoTests.class);

    @Test
    public void whenCOnvertingEntityToDto_thenCorrect() {
        User userOwner = createOwnerUser();
        UserDto userDto = UserDto.modelMapper.map(userOwner, UserDto.class);



        User userClient = createClientUser();
        Accommodation accommodation1 = createAccomodationObject(userOwner);
        Booking booking1 = createBooking(userClient, accommodation1);

    }
}
