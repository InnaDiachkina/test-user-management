package example.test.user.management.service;

import example.test.user.management.dto.UserRequestDto;
import example.test.user.management.dto.UserResponseDto;
import example.test.user.management.dto.UserUpdatedFieldDto;
import java.time.LocalDate;
import java.util.List;

public interface UserService {
    UserResponseDto createUser(UserRequestDto requestDto);

    UserResponseDto updateUser(Long id, UserRequestDto requestDto);

    UserResponseDto updateFieldUser(Long id, UserUpdatedFieldDto requestDto);

    void deleteUser(Long id);

    List<UserResponseDto> findAllByBirthDateBetween(LocalDate from, LocalDate to);

}
