package com.kopec.wojciech.engineers_thesis.model;

import org.junit.Test;

public class AccommodationValidationTests extends AbstractValidationTest<Accommodation> {

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