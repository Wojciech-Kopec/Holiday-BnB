package com.kopec.wojciech.engineers_thesis.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class GuestsCountBiggerThanAvailable extends RuntimeException {

    public GuestsCountBiggerThanAvailable(final String message) {
        super(message);
    }
}
