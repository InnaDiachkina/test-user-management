package example.test.user.management.dto;

import example.test.user.management.model.User;

public interface UserMapper {
    User toModel(UserRequestDto requestDto);

    UserResponseDto toDto(User user);
}
