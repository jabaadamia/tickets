package ge.ticketebi.ticketebi_backend.services.impl;

import ge.ticketebi.ticketebi_backend.domain.dto.UserRequestDto;
import ge.ticketebi.ticketebi_backend.domain.dto.UserResponseDto;
import ge.ticketebi.ticketebi_backend.domain.entities.User;
import ge.ticketebi.ticketebi_backend.mappers.Mapper;
import ge.ticketebi.ticketebi_backend.repositories.UserRepository;
import ge.ticketebi.ticketebi_backend.services.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Mapper<User, UserRequestDto> userMapper;

    public UserServiceImpl(
            UserRepository userRepository,
            Mapper<User, UserRequestDto> userMapper
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponseDto registerUser(UserRequestDto dto) {
        return null;
    }

    @Override
    public UserResponseDto getCurrentUser() {
        return null;
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        return null;
    }

    @Override
    public UserResponseDto updateUser(Long id, UserRequestDto dto) {
        return null;
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return List.of();
    }

    @Override
    public void enableUser(Long id) {

    }

    @Override
    public void disableUser(Long id) {

    }

    @Override
    public void deleteUser(Long id) {

    }

}
