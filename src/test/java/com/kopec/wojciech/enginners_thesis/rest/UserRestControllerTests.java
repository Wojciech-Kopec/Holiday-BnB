package com.kopec.wojciech.enginners_thesis.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kopec.wojciech.enginners_thesis.dto.UserDto;
import com.kopec.wojciech.enginners_thesis.model.ModelProvider;
import com.kopec.wojciech.enginners_thesis.service.UserService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

//@RunWith(MockitoJUnitRunner.class)
@RunWith(SpringRunner.class)
@WebMvcTest(value = UserRestController.class, secure = false)
public class UserRestControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @InjectMocks
    private UserRestController userRestController;
    private ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);


    public String mock(UserDto requestedUser) throws JsonProcessingException {
        //2nd User used for List assertions
        UserDto existingUser = UserDto.toDto(ModelProvider.createUser_2());

        Mockito.when(userService.save(requestedUser)).thenReturn(requestedUser);
        Mockito.when(userService.update(requestedUser)).thenReturn(requestedUser);
        Mockito.when(userService.findByUsername(requestedUser.getUsername())).thenReturn(requestedUser);
        Mockito.when(userService.findByUsernameContaining(requestedUser.getUsername())).thenReturn(Collections
                .singletonList(requestedUser));
        Mockito.when(userService.findAll()).thenReturn(Arrays.asList(requestedUser, existingUser));

        userRestController = new UserRestController(userService);
        return mapper.writeValueAsString(requestedUser);

    }

    @Test
    public void validRegistrationTest() throws Exception {
        //Given
        UserDto requestedUser = UserDto.toDto(ModelProvider.createUser_1());
        String userJson = mock(requestedUser);

        //When
        String location = "/api/users";
        MockHttpServletResponse
                response = executeRequest(HttpMethod.POST, userJson, location);
        printResponseDetails(response);

        //Then
        thenAssert(response, userJson, HttpStatus.CREATED, location, null);

    }

    @Test
    public void invalidRegistrationTest() throws Exception {
        //Given
        UserDto requestedUser = UserDto.toDto(ModelProvider.createUser_1());
        requestedUser.setId(10);
        String userJson = mock(requestedUser);

        //When
        String location = "/api/users";
        MockHttpServletResponse response = executeRequest(HttpMethod.POST, userJson, location);
        printResponseDetails(response);

        //Then
        thenAssert(response, null, HttpStatus.BAD_REQUEST, null, "{resource.id_set}");
    }

    @Test
    public void validEndpointUpdateUserTest() throws Exception {
        //Given
        UserDto requestedUser = UserDto.toDto(ModelProvider.createUser_1());
        requestedUser.setId(10);
        String userJson = mock(requestedUser);

        //When
        String location = "/api/users/" + requestedUser.getId();
        MockHttpServletResponse response = executeRequest(HttpMethod.PUT, userJson, location);
        printResponseDetails(response);

        //Then
        thenAssert(response, userJson, HttpStatus.OK, null, null);
    }

    @Test
    public void invalidEndpointUpdateUserTest() throws Exception {
        //Given
        UserDto requestedUser = UserDto.toDto(ModelProvider.createUser_1());
        requestedUser.setId(10);
        String userJson = mock(requestedUser);

        //When
        String location = "/api/users/" + (requestedUser.getId() + 1);
        MockHttpServletResponse response = executeRequest(HttpMethod.PUT, userJson, location);
        printResponseDetails(response);

        //Then
        thenAssert(response, null, HttpStatus.BAD_REQUEST, null, "{resource.id_not_consistent}");
    }

    private MockHttpServletResponse executeRequest(HttpMethod httpMethod, String content, String urlTemplate) throws
            Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .request(httpMethod, urlTemplate)
                .accept(MediaType.APPLICATION_JSON_UTF8).content(content)
                .contentType(MediaType.APPLICATION_JSON_UTF8);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        return result.getResponse();
    }

    private void printResponseDetails(MockHttpServletResponse response) throws UnsupportedEncodingException {
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

    private void thenAssert(MockHttpServletResponse response, String content, HttpStatus status, String urlLocation,
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
