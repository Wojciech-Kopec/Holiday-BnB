package com.kopec.wojciech.enginners_thesis.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kopec.wojciech.enginners_thesis.dto.UserDto;
import com.kopec.wojciech.enginners_thesis.model.ModelProvider;
import com.kopec.wojciech.enginners_thesis.service.UserService;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserRestController.class, secure = false)
public class UserRestControllerTests extends AbstractRestUnitTests<UserDto> {

    @MockBean
    private UserService userService;

    @InjectMocks
    private UserRestController userRestController;

    private String baseEndpoint = "/api/users";
    private UserDto requestedUser;
    private UserDto existingUser;


    public void mockServices() throws JsonProcessingException {
        Mockito.when(userService.save(requestedUser)).thenReturn(requestedUser);
        Mockito.when(userService.update(requestedUser)).thenReturn(requestedUser);
        Mockito.when(userService.findByUsername(requestedUser.getUsername())).thenReturn(requestedUser);
        Mockito.when(userService.findByUsernameContaining(requestedUser.getUsername())).thenReturn(Collections
                .singletonList(requestedUser));
        Mockito.when(userService.findAll()).thenReturn(Arrays.asList(requestedUser, existingUser));
        Mockito.when(userService.findById(requestedUser.getId())).thenReturn(requestedUser);
        userRestController = new UserRestController(userService);
    }

    @Before
    //Resets objects to their original state
    public void setUpBaseObject() {
        requestedUser = UserDto.toDto(ModelProvider.createUser_1());
        //2nd User used for List assertions
        existingUser = UserDto.toDto(ModelProvider.createUser_2());
    }

    @Test
    public void validRegistrationTest() throws Exception {
        //Given
        mockServices();
        String userJson = mapper.writeValueAsString(requestedUser);

        //When
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.POST, userJson, baseEndpoint);
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        printResponseDetails(response);

        //Then
        thenAssert(response, userJson, HttpStatus.CREATED, baseEndpoint, null);
    }

    @Test
    public void invalidRegistrationTest() throws Exception {
        //Given
        requestedUser.setId(10);
        mockServices();
        String userJson = mapper.writeValueAsString(requestedUser);

        //When
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.POST, userJson, baseEndpoint);
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        printResponseDetails(response);

        //Then
        thenAssert(response, null, HttpStatus.BAD_REQUEST, null, "{resource.id_set}");
    }

    @Test
    public void validEndpointUpdateUserTest() throws Exception {
        //Given
        requestedUser.setId(10);
        mockServices();
        String userJson = mapper.writeValueAsString(requestedUser);

        //When
        Integer pathId = requestedUser.getId();
        String location = baseEndpoint + "/" + pathId;
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.PUT, userJson, location);
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        printResponseDetails(response);

        //Then
        thenAssert(response, userJson, HttpStatus.OK, null, null);
    }

    @Test
    public void invalidEndpointUpdateUserTest() throws Exception {
        //Given
        requestedUser.setId(10);
        mockServices();
        String userJson = mapper.writeValueAsString(requestedUser);

        //When
        Integer pathId = requestedUser.getId() + 1;
        String location = baseEndpoint + "/" + pathId;
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.PUT, userJson, location);
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        printResponseDetails(response);

        //Then
        thenAssert(response, null, HttpStatus.BAD_REQUEST, null, "{resource.id_not_consistent}");
    }

    @Test
    public void validFindAllUsersNoParamTest() throws Exception {
        //Given
        mockServices();
        List<UserDto> users = Lists.newArrayList(requestedUser, existingUser);
        String usersJson = mapper.writeValueAsString(users);

        //When
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.GET, null, baseEndpoint);
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        printResponseDetails(response);

        //Then
        thenAssert(response, usersJson, HttpStatus.OK, null, null);
    }

    @Test
    public void validFindAllUsersWithParamTest() throws Exception {
        //Given
        mockServices();
        List<UserDto> users = Lists.newArrayList(requestedUser);
        String usersJson = mapper.writeValueAsString(users);

        //When
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.GET, null, baseEndpoint);
        request.param("username", requestedUser.getUsername());
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        printResponseDetails(response);

        //Then
        thenAssert(response, usersJson, HttpStatus.OK, null, null);
    }

    @Test
    public void validDeleteUserTest() throws Exception {
        //Given
        requestedUser.setId(10);
        mockServices();
        String userJson = mapper.writeValueAsString(requestedUser);

        //When
        Integer pathId = requestedUser.getId();
        String location = baseEndpoint + "/" + pathId;
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.DELETE, null, location);
        request.param("id", requestedUser.getId().toString());
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        printResponseDetails(response);

        //Then
        thenAssert(response, null, HttpStatus.NO_CONTENT, null, null);
    }

    @Test
    public void invalidDeleteUserTest() throws Exception {
        //Given
        requestedUser.setId(10);
        mockServices();

        //When
        Integer pathId = requestedUser.getId() + 1;
        String location = baseEndpoint + "/" + pathId;
        MockHttpServletRequestBuilder request = buildRequest(HttpMethod.DELETE, null, location);
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        printResponseDetails(response);

        //Then
        thenAssert(response, null, HttpStatus.NOT_FOUND, null, null);
    }
}
