package com.kopec.wojciech.enginners_thesis;

import com.kopec.wojciech.enginners_thesis.integration.AccommodationResourceIT;
import com.kopec.wojciech.enginners_thesis.integration.BookingResourceIT;
import com.kopec.wojciech.enginners_thesis.integration.UserResourceIT;
import com.kopec.wojciech.enginners_thesis.repository.AccommodationRepositoryTests;
import com.kopec.wojciech.enginners_thesis.repository.BookingRepositoryTests;
import com.kopec.wojciech.enginners_thesis.repository.UserRepositoryTests;
import com.kopec.wojciech.enginners_thesis.rest.AccommodationRestControllerTests;
import com.kopec.wojciech.enginners_thesis.rest.BookingRestControllerTests;
import com.kopec.wojciech.enginners_thesis.rest.UserRestControllerTests;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class TestConfiguration {

    @Bean
    public UserRepositoryTests userRepositoryTests() {
        return new UserRepositoryTests();
    }

    @Bean
    public AccommodationRepositoryTests accommodationRepositoryTests() {
        return new AccommodationRepositoryTests();
    }

    @Bean
    public BookingRepositoryTests bookingRepositoryTests() {
        return new BookingRepositoryTests();
    }
}
