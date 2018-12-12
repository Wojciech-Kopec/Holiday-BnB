package com.kopec.wojciech.enginners_thesis.model;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccommodationValidation implements TestableValidation<Accommodation> {
    private final Logger logger = LoggerFactory.getLogger(AccommodationValidation.class);

    @Test
    public void shouldNotValidateNulls() {
        Accommodation accommodation= new Accommodation();
        assertObjectsConstraintsViolationCount(accommodation, 7);
    }

    @Test
    public void shouldNotValidateSizesNotInRange() {
        Accommodation accommodation = Accommodation.builder()
                .name(stringWithSize(9))
                .description(stringWithSize(99))
                .maxGuests(-1)
                .pricePerNight(-1)
                .user(new User())
                .localization(new Localization())
                .build();

        assertObjectsConstraintsViolationCount(accommodation, 5);
    }
}