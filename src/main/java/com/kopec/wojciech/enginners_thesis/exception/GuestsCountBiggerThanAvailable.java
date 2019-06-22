package com.kopec.wojciech.enginners_thesis.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "")
public class GuestsCountBiggerThanAvailable extends RuntimeException {

    public GuestsCountBiggerThanAvailable(final String message) {
        super(message);
    }
}