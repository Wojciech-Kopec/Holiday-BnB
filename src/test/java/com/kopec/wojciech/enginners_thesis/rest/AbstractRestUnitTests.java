package com.kopec.wojciech.enginners_thesis.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

abstract public class AbstractRestUnitTests<T> {

    @Autowired
    protected MockMvc mockMvc;

    protected ObjectMapper mapper = new ObjectMapper();

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    abstract protected void mockServices() throws JsonProcessingException;

    protected MockHttpServletRequestBuilder buildRequest(HttpMethod httpMethod, String content, String urlTemplate) throws Exception {
        if(content==null){
            return buildRequest(httpMethod,urlTemplate);
        } else {
            return buildRequest(httpMethod,urlTemplate)
                    .accept(MediaType.APPLICATION_JSON_UTF8).content(content)
                    .contentType(MediaType.APPLICATION_JSON_UTF8);
        }
    }

    protected MockHttpServletRequestBuilder buildRequest(HttpMethod httpMethod, String urlTemplate) throws Exception {
        return MockMvcRequestBuilders
                .request(httpMethod, urlTemplate);
    }

    protected void printResponseDetails(MockHttpServletResponse response) throws UnsupportedEncodingException {
        logger.info("response.getContentType():\n"
                + response.getContentType() + "\n");

        logger.info("response.getHeader(HttpHeaders.LOCATION):\n"
                + response.getHeader(HttpHeaders.LOCATION + "\n"));

        logger.info("response.getStatus():\n"
                + response.getStatus() + "\n");

        logger.info("response.getContentAsString():\n"
                + response.getContentAsString() + "\n");

        logger.info("response.getErrorMessage():\n"
                + response.getErrorMessage() + "\n");
    }

    protected void thenAssert(MockHttpServletResponse response, String content, HttpStatus status, String urlLocation,
                              String error) throws UnsupportedEncodingException {

        assertThat(response.getContentAsString(), equalTo(content != null ? content : ""));
        assertThat(response.getStatus(), equalTo(status.value()));
        assertThat(response.getErrorMessage(), equalTo(error));

        if (urlLocation != null) {
            assertThat(response.getHeader(HttpHeaders.LOCATION), containsString(urlLocation));
        } else {
            assertThat(response.getHeader(HttpHeaders.LOCATION), nullValue());
        }
        assertThat(response.getContentType(), equalTo(content != null ? MediaType.APPLICATION_JSON_UTF8_VALUE : null));
    }
}
