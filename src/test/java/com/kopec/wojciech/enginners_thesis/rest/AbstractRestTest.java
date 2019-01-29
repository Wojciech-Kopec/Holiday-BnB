package com.kopec.wojciech.enginners_thesis.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.UnsupportedEncodingException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

//@WebMvcTest(value = {UserRestController.class, AccommodationRestController.class, BookingRestController.class}, secure = false)
abstract public class AbstractRestTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper mapper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    abstract protected void mockServices();

    protected MockHttpServletRequestBuilder buildRequest(HttpMethod httpMethod, String content, String urlTemplate) {
        if (content == null) {
            return MockMvcRequestBuilders
                    .request(httpMethod, urlTemplate);
        } else {
            return MockMvcRequestBuilders
                    .request(httpMethod, urlTemplate)
                    .accept(MediaType.APPLICATION_JSON_UTF8).content(content)
                    .contentType(MediaType.APPLICATION_JSON_UTF8);
        }
    }

    protected void printResponseDetails(MockHttpServletResponse response) throws UnsupportedEncodingException {
        logger.info("response.getContentType():\n"
                + response.getContentType() + "\n");

        logger.info("response.getHeader(HttpHeaders.LOCATION):\n"
                + response.getHeader(HttpHeaders.LOCATION) + "\n");

        logger.info("response.getStatus():\n"
                + response.getStatus() + "\n");

        logger.info("response.getContentAsString():\n"
                + response.getContentAsString() + "\n");

        logger.info("response.getErrorMessage():\n"
                + response.getErrorMessage() + "\n");
    }

    protected void thenAssert(MockHttpServletResponse response, String expectedResponseBody, HttpStatus expectedStatus,
                              String expectedLocation, String expectedErrorMsg) throws UnsupportedEncodingException {

        assertThat(response.getStatus(), equalTo(expectedStatus.value()));
        assertThat(response.getContentAsString(), equalTo(expectedResponseBody));
        assertThat(response.getErrorMessage(), equalTo(expectedErrorMsg));

        if (expectedLocation != null) {
            assertThat(response.getHeader(HttpHeaders.LOCATION), containsString(expectedLocation));
        } else {
            assertThat(response.getHeader(HttpHeaders.LOCATION), is(nullValue()));
        }
        assertThat(response.getContentType(), equalTo(expectedResponseBody.length() != 0 ?
                MediaType.APPLICATION_JSON_UTF8_VALUE : null));
    }


    public void mockedHttpTestTemplate(HttpMethod requestMethod, String requestLocation,
                                       Object requestBodyObj, Object expectedResponseBodyObj,
                                       HttpStatus expectedStatus, String expectedLocation, String expectedErrorMsg) {
        mockServices();
        httpTestTemplate(requestMethod, requestLocation, requestBodyObj, expectedResponseBodyObj,
                expectedStatus, expectedLocation, expectedErrorMsg);
    }

    public void httpTestTemplate(HttpMethod requestMethod, String requestLocation, Object requestBodyObj, Object expectedResponseBodyObj, HttpStatus expectedStatus, String expectedLocation, String expectedErrorMsg) {
        try {
            //Given
            String requestBody = requestBodyObj != null ? mapper.writeValueAsString(requestBodyObj) : "";
            String expectedResponseBody = expectedResponseBodyObj != null ? mapper.writeValueAsString(expectedResponseBodyObj) : "";

            //When
            MockHttpServletRequestBuilder request = buildRequest(requestMethod, requestBody, requestLocation);
            MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
            printResponseDetails(response);

            //Then
            //TODO TBA how about using MockMVC build-in assertion checker?
            thenAssert(response,
                    expectedResponseBody,
                    expectedStatus,
                    expectedLocation,
                    expectedErrorMsg);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
