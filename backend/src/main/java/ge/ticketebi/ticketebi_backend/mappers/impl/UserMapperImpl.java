package ge.ticketebi.ticketebi_backend.mappers.impl;

import ge.ticketebi.ticketebi_backend.domain.dto.UserRequestDto;
import ge.ticketebi.ticketebi_backend.domain.dto.UserResponseDto;
import ge.ticketebi.ticketebi_backend.domain.entities.AuthProvider;
import ge.ticketebi.ticketebi_backend.domain.entities.Role;
import ge.ticketebi.ticketebi_backend.domain.entities.User;
import ge.ticketebi.ticketebi_backend.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements Mapper<User, UserRequestDto> {

    private final ModelMapper modelMapper;

    public UserMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Map from UserRequestDto to User entity.
     * Some fields (password encoding, role, enabled, authProvider) are set manually.
     */
    @Override
    public User mapFrom(UserRequestDto dto) {
        if (dto == null) return null;

        User user = modelMapper.map(dto, User.class);
        user.setPassword(dto.getPassword()); // encode in service
        user.setEnabled(false);
        user.setRole(Role.CUSTOMER);
        user.setAuthProvider(AuthProvider.LOCAL);

        return user;
    }

    /**
     * This satisfies the Mapper interface: User → UserRequestDto.
     * Mainly used internally or to satisfy the compiler.
     */
    @Override
    public UserRequestDto mapTo(User user) {
        if (user == null) return null;
        return modelMapper.map(user, UserRequestDto.class);
    }

    /**
     * Real method for API responses: User → UserResponseDto.
     */
    public UserResponseDto mapToResponse(User user) {
        if (user == null) return null;
        return modelMapper.map(user, UserResponseDto.class);
    }

}
