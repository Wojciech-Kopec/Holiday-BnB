package com.kopec.wojciech.enginners_thesis;

import com.kopec.wojciech.enginners_thesis.repository.AccommodationRepositoryTests;
import com.kopec.wojciech.enginners_thesis.repository.UserRepositoryTests;
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


}
