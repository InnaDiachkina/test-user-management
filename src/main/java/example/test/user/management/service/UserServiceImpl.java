package example.test.user.management.service;

import example.test.user.management.dto.UserMapper;
import example.test.user.management.dto.UserRequestDto;
import example.test.user.management.dto.UserResponseDto;
import example.test.user.management.dto.UserUpdatedFieldDto;
import example.test.user.management.exception.UserNotFoundException;
import example.test.user.management.model.User;
import example.test.user.management.repository.UserRepository;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
       this.userMapper = userMapper;
    }

    @Override
    public UserResponseDto createUser(UserRequestDto requestDto) {
        if (Period.between(requestDto.getBirthDate(), LocalDate.now()).getYears() < 18) {
            throw new RuntimeException("User age must be at least 18 years");
        }
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RuntimeException("User with this email already exists");
        }
        return userMapper.toDto(userRepository.save(userMapper.toModel(requestDto)));
    }

    @Override
    public UserResponseDto updateUser(Long id, UserRequestDto requestDto) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User with id " + id + " not found"));
        user.setEmail(requestDto.getEmail());
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setBirthDate(requestDto.getBirthDate());
        user.setAddress(requestDto.getAddress());
        user.setPhoneNumber(requestDto.getPhoneNumber());
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto updateFieldUser(Long id, UserUpdatedFieldDto requestDto) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User with id " + id + " not found"));
        if (requestDto.getEmail() != null) {
            user.setEmail(requestDto.getEmail());
        }
        if (requestDto.getFirstName() != null) {
            user.setFirstName(requestDto.getFirstName());
        }
        if (requestDto.getLastName() != null) {
            user.setLastName(requestDto.getLastName());
        }
        if (requestDto.getBirthDate() != null) {
            user.setBirthDate(requestDto.getBirthDate());
        }
        if (requestDto.getAddress() != null) {
            user.setAddress(requestDto.getAddress());
        }
        if (requestDto.getPhoneNumber() != null) {
            user.setPhoneNumber(requestDto.getPhoneNumber());
        }
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User with id " + id + " not found"));
        userRepository.deleteById(id);
    }

    @Override
    public List<UserResponseDto> findAllByBirthDateBetween(LocalDate from, LocalDate to) {
        return userRepository.findAllByBirthDateBetween(from, to)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }
}
