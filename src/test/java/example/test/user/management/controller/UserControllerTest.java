package example.test.user.management.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.test.user.management.dto.UserRequestDto;
import example.test.user.management.service.UserService;
import example.test.user.management.dto.UserResponseDto;
import java.time.LocalDate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService service;

    @Test
    @DisplayName("""
            Check create user with valid data
            """)
    public void create_userWithValidData_thenReturnSavedUser()
            throws Exception {
        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setEmail("john.doe@example.com");
        requestDto.setFirstName("John");
        requestDto.setLastName("Doe");
        requestDto.setBirthDate(LocalDate.parse("2000-01-01"));

        UserResponseDto expected = new UserResponseDto();
        expected.setEmail("john.doe@example.com");
        expected.setFirstName("John");
        expected.setLastName("Doe");
        expected.setBirthDate(LocalDate.parse("2000-01-01"));

        when(service.createUser(requestDto)).thenReturn(expected);

        MvcResult result = mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        UserResponseDto actual = objectMapper.readValue(response,
                UserResponseDto.class);

        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getBirthDate(), actual.getBirthDate());
    }
}
