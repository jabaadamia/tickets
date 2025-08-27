package ge.ticketebi.ticketebi_backend.services;

import ge.ticketebi.ticketebi_backend.domain.dto.UserRequestDto;
import ge.ticketebi.ticketebi_backend.domain.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto registerUser(UserRequestDto dto);
    UserResponseDto getCurrentUser();
    UserResponseDto getUserById(Long id);
    UserResponseDto updateUser(Long id, UserRequestDto dto);
    List<UserResponseDto> getAllUsers();
    void enableUser(Long id);
    void disableUser(Long id);
    void deleteUser(Long id);
}
