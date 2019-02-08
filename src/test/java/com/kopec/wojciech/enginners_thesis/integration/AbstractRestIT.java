package com.kopec.wojciech.enginners_thesis.integration;

import com.kopec.wojciech.enginners_thesis.dto.AccommodationDto;
import com.kopec.wojciech.enginners_thesis.dto.BookingDto;
import com.kopec.wojciech.enginners_thesis.dto.UserDto;
import com.kopec.wojciech.enginners_thesis.model.Accommodation;
import com.kopec.wojciech.enginners_thesis.model.Booking;
import com.kopec.wojciech.enginners_thesis.model.User;
import com.kopec.wojciech.enginners_thesis.repository.AccommodationRepository;
import com.kopec.wojciech.enginners_thesis.repository.BookingRepository;
import com.kopec.wojciech.enginners_thesis.repository.UserRepository;
import com.kopec.wojciech.enginners_thesis.rest.AbstractRestTest;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static com.kopec.wojciech.enginners_thesis.model.ModelProvider.*;


/**
 * This class contains required annotation-based Spring configuration to run Integration Tests.
 * Above that it automatically autowires all available Repositories to any extending class and initially configures database
 * That is - pushing 2 instances of every Model classes
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(secure = false, addFilters = false)
@TestPropertySource(locations = "classpath:application.properties")
public abstract class AbstractRestIT extends AbstractRestTest {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected AccommodationRepository accommodationRepository;

    @Autowired
    protected BookingRepository bookingRepository;

    protected UserDto primaryUserDto;
    protected UserDto secondaryUserDto;

    protected AccommodationDto primaryAccommodationDto;
    protected AccommodationDto secondaryAccommodationDto;

    protected BookingDto primaryBookingDto;
    protected BookingDto secondaryBookingDto;

    /**
     *
     */
    @Before
    public void setUp() {
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
    public void tearDown() {
        bookingRepository.deleteAll();
        accommodationRepository.deleteAll();
        userRepository.deleteAll();
    }
}
