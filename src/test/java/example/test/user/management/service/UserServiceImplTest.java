package example.test.user.management.service;

import static org.junit.jupiter.api.Assertions.*;

import example.test.user.management.dto.UserMapper;
import example.test.user.management.dto.UserRequestDto;
import example.test.user.management.dto.UserResponseDto;
import example.test.user.management.model.User;
import example.test.user.management.repository.UserRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("""
            Check create User with valid data
            """)
    public void createUser_withValidDate_thenReturnSavedUser() {
        UserResponseDto expected = new UserResponseDto();
        expected.setId(1L);
        expected.setEmail("bob@test.com");
        expected.setFirstName("Bob");
        expected.setLastName("Alison");
        expected.setBirthDate(LocalDate.parse("2000-01-01"));

        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setEmail("bob@test.com");
        requestDto.setFirstName("Bob");
        requestDto.setLastName("Alison");
        requestDto.setBirthDate(LocalDate.parse("2000-01-01"));

        User user = new User();
        user.setId(1L);
        user.setEmail("bob@test.com");
        user.setFirstName("Bob");
        user.setLastName("Alison");
        user.setBirthDate(LocalDate.parse("2000-01-01"));

        Mockito.when(userRepository.existsByEmail(requestDto.getEmail()))
                .thenReturn(false);
        Mockito.when(userMapper.toModel(requestDto)).thenReturn(user);
        Mockito.when(userRepository.save(user))
                .thenReturn(user);
        Mockito.when(userMapper.toDto(user)).thenReturn(expected);
        UserResponseDto actual = userService.createUser(requestDto);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Check create User with existing email
            """)
    public void createUser_withExistingEmail_thenReturnException() {
        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setEmail("bob@test.com");
        requestDto.setFirstName("Bob");
        requestDto.setLastName("Alison");
        requestDto.setBirthDate(LocalDate.parse("2000-01-01"));

        Mockito.when(userRepository.existsByEmail(requestDto.getEmail()))
                .thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.createUser(requestDto));
        assertEquals("User with this email already exists", exception.getMessage());
    }

    @Test
    @DisplayName("""
            Check create User with age less than 18
            """)
    public void createUser_withAgeLessThan18_thenReturnException() {
        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setEmail("bob@test.com");
        requestDto.setFirstName("Bob");
        requestDto.setLastName("Alison");
        requestDto.setBirthDate(LocalDate.parse("2015-01-01"));

        RuntimeException exception =
                assertThrows(RuntimeException.class, () -> userService.createUser(requestDto));
        assertEquals("User age must be at least 18 years", exception.getMessage());
    }
}
