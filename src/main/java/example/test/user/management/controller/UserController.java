package example.test.user.management.controller;

import example.test.user.management.dto.UserRequestDto;
import example.test.user.management.dto.UserResponseDto;
import example.test.user.management.dto.UserUpdatedFieldDto;
import example.test.user.management.service.UserService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponseDto create(@RequestBody @Valid UserRequestDto requestDto) {
        return userService.createUser(requestDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    public UserResponseDto update(@PathVariable Long id,
                                  @RequestBody @Valid UserRequestDto requestDto) {
        return userService.updateUser(id, requestDto);
    }

    @PatchMapping("/{id}")
    public UserResponseDto updateField(@PathVariable Long id,
                                       @RequestBody @Valid UserUpdatedFieldDto requestDto) {
        return userService.updateFieldUser(id, requestDto);
    }

    @GetMapping
    public List<UserResponseDto> findAllByBirthDateBetween(@RequestParam LocalDate from,
                                                           @RequestParam LocalDate to) {
        return userService.findAllByBirthDateBetween(from, to);
    }
}
