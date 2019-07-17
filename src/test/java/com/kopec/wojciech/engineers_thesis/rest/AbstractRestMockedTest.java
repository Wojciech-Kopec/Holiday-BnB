package com.kopec.wojciech.engineers_thesis.rest;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

abstract public class AbstractRestMockedTest extends AbstractRestTest {

    abstract protected void mockServices();

    protected void mockedHttpTestTemplate(HttpMethod requestMethod, String requestLocation,
                                       Object requestBodyObj, Object expectedResponseBodyObj,
                                       HttpStatus expectedStatus, String expectedLocation, String expectedErrorMsg) {
        mockServices();

        httpTestTemplate(requestMethod, requestLocation, requestBodyObj, expectedResponseBodyObj,
                expectedStatus, expectedLocation, expectedErrorMsg);
    }
}
