package com.kopec.wojciech.engineers_thesis.integration;

import com.kopec.wojciech.engineers_thesis.dto.AccommodationDto;
import com.kopec.wojciech.engineers_thesis.dto.BookingDto;
import com.kopec.wojciech.engineers_thesis.dto.UserDto;
import com.kopec.wojciech.engineers_thesis.model.Accommodation;
import com.kopec.wojciech.engineers_thesis.model.Booking;
import com.kopec.wojciech.engineers_thesis.model.User;
import com.kopec.wojciech.engineers_thesis.repository.*;
import com.kopec.wojciech.engineers_thesis.rest.AbstractRestTest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static com.kopec.wojciech.engineers_thesis.utils.ModelProvider.*;


/**
 * This class contains required annotation-based Spring configuration to run Integration Tests.
 * Above that it automatically auto-wires all available Repositories to any extending class and initially configures database
 * That is - pushing 2 instances of every Model classes
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(secure = false, addFilters = false)
@TestPropertySource(locations = "classpath:application-it.properties")
public abstract class AbstractRestIT extends AbstractRestTest {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected AccommodationRepository accommodationRepository;

    @Autowired
    protected BookingRepository bookingRepository;

    @Autowired
    protected LocalizationRepository localizationRepository;

    @Autowired
    protected AmenityRepository amenityRepository;

    protected UserDto primaryUserDto;
    protected UserDto secondaryUserDto;

    protected AccommodationDto primaryAccommodationDto;
    protected AccommodationDto secondaryAccommodationDto;

    protected BookingDto primaryBookingDto;
    protected BookingDto secondaryBookingDto;

    @Before
    public void setUp() {
        cleanUpDbTables();

        User primaryUser = userRepository.save(createUser_1_noId());
        User secondaryUser = userRepository.save(createUser_2_noId());
        primaryUserDto = UserDto.toDto(primaryUser);
        secondaryUserDto = UserDto.toDto(secondaryUser);

        Accommodation primaryAccommodation = accommodationRepository.save(createAccommodation_1_noId(primaryUser));
        Accommodation secondaryAccommodation = accommodationRepository.save(createAccommodation_2_noId(secondaryUser));
        primaryAccommodationDto = AccommodationDto.toDto(primaryAccommodation);
        secondaryAccommodationDto = AccommodationDto.toDto(secondaryAccommodation);

        Booking primaryBooking = bookingRepository.save(createBooking_1_noId(secondaryUser, primaryAccommodation));
        Booking secondaryBooking = bookingRepository.save(createBooking_2_noId(primaryUser, secondaryAccommodation));

        primaryBookingDto = BookingDto.toDto(primaryBooking);
        secondaryBookingDto = BookingDto.toDto(secondaryBooking);
    }

    @After
    public void cleanUpDbTables() {
        //FIXME
        amenityRepository.deleteAll();
        bookingRepository.deleteAll();
        accommodationRepository.deleteAll();
        userRepository.deleteAll();
    }

    @AfterClass
    public static void cleanUp() {
        System.out.println("Tests are over!\nHook for Debug");
    }
}
