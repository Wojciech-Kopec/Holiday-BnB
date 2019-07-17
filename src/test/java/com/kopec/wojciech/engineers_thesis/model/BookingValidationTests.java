package com.kopec.wojciech.engineers_thesis.model;

import org.junit.Test;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;

public class BookingValidationTests extends AbstractValidationTest<Booking> {

    @Test
    public void shouldNotValidateNulls() {
        Booking booking= new Booking();
        assertObjectsConstraintsViolationCount(booking, 7);
    }

    @Test
    public void shouldNotValidateSizesNotInRange() {
        Booking booking1 = Booking.builder()
                .guestsCount(0)
                .build();

        assertObjectsConstraintsViolationCount(booking1, 7);
    }

    @Test
    public void shouldValidateDates() {
        Booking booking = new Booking();

        //startDate validation
        Map<LocalDate, LocalDate> startDateFinishDateMap = new HashMap<>();
        startDateFinishDateMap.put(LocalDate.now().minus(Period.ofDays(1)), LocalDate.now().plus(Period.ofDays(1)));
        startDateFinishDateMap.put(LocalDate.now(), LocalDate.now().plus(Period.ofDays(1)));

        for (Map.Entry<LocalDate, LocalDate> entry : startDateFinishDateMap.entrySet()) {
            booking.setStartDate(entry.getKey());
            booking.setFinishDate(entry.getValue());
            logger.info("Testing for value: " + entry.toString());
            assertFieldValidation(booking, "startDate", false, null);
        }
        //finishDate validation
        startDateFinishDateMap.clear();
        startDateFinishDateMap.put(LocalDate.now().plus(Period.ofDays(1)), LocalDate.now().minus(Period.ofDays(1)));
        startDateFinishDateMap.put(LocalDate.now().plus(Period.ofDays(1)), LocalDate.now());

        for (Map.Entry<LocalDate, LocalDate> entry : startDateFinishDateMap.entrySet()) {
            booking.setStartDate(entry.getKey());
            booking.setFinishDate(entry.getValue());
            logger.info("Testing for value: " + entry.toString());
            assertFieldValidation(booking, "finishDate", false, null);
        }

        //finishDateAfterStartDate validation
        startDateFinishDateMap.clear();
        startDateFinishDateMap.put(LocalDate.now().plus(Period.ofDays(2)), LocalDate.now().plus(Period.ofDays(2)));
        startDateFinishDateMap.put(LocalDate.now().plus(Period.ofDays(2)), LocalDate.now().plus(Period.ofDays(1)));

        for (Map.Entry<LocalDate, LocalDate> entry : startDateFinishDateMap.entrySet()) {
            booking.setStartDate(entry.getKey());
            booking.setFinishDate(entry.getValue());
            logger.info("Testing for value: " + entry.toString());
            final String FINISH_DATE_AFTER_START_MSG = "Finish date must be after start date";
            assertFieldValidation(booking, "finishDateAfterStartDate", false, FINISH_DATE_AFTER_START_MSG);
        }
    }
}
