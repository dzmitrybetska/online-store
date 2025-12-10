package com.upgrade.store.api.dto.mapper;

import com.upgrade.store.api.dto.request.RegisterRequest;
import com.upgrade.store.api.dto.response.UserShortResponse;
import com.upgrade.store.domain.model.User;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true),
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    User mapToEntity(RegisterRequest registerRequest);

    @Mapping(target = "roles", expression = "java(user.getRoles().stream()" +
            ".map(role -> role.getName().name())" +
            ".collect(java.util.stream.Collectors.toSet()))")
    UserShortResponse mapToDto(User user);
}
