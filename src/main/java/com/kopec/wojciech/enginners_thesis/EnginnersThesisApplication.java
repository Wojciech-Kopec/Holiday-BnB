package com.kopec.wojciech.enginners_thesis;

import com.kopec.wojciech.enginners_thesis.dto.AccommodationDto;
import com.kopec.wojciech.enginners_thesis.dto.BookingDto;
import com.kopec.wojciech.enginners_thesis.dto.UserDto;
import com.kopec.wojciech.enginners_thesis.model.*;
import com.kopec.wojciech.enginners_thesis.repository.AccommodationRepository;
import com.kopec.wojciech.enginners_thesis.repository.BookingRepository;
import com.kopec.wojciech.enginners_thesis.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import static com.kopec.wojciech.enginners_thesis.ModelProvider.*;
import static com.kopec.wojciech.enginners_thesis.ModelProvider.createUser_2_noId;


@SpringBootApplication
public class EnginnersThesisApplication {
    private static final Logger logger = LoggerFactory.getLogger(EnginnersThesisApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(EnginnersThesisApplication.class, args);
        logger.info("Application started");
        persistEntities(context);

    }

    private static void persistEntities(ApplicationContext context) {
        UserRepository userRepository = context.getBean(UserRepository.class);
        AccommodationRepository accommodationRepository = context.getBean(AccommodationRepository.class);
        BookingRepository bookingRepository = context.getBean(BookingRepository.class);

        logger.info("START: Persisting entities at Application Start-up");
        User primaryUser = userRepository.save(createUser_1_noId());
        User secondaryUser = userRepository.save(createUser_2_noId());

        Accommodation primaryAccommodation = accommodationRepository.save(createAccommodation_1_noId(primaryUser));
        Accommodation secondaryAccommodation = accommodationRepository.save(createAccommodation_2_noId(secondaryUser));

        Booking primaryBooking = bookingRepository.save(createBooking_1_noId(secondaryUser, primaryAccommodation));
        Booking secondaryBooking = bookingRepository.save(createBooking_2_noId(primaryUser, secondaryAccommodation));
        logger.info("FINISHED: Persisting entities at Application Start-up");

    }
}
