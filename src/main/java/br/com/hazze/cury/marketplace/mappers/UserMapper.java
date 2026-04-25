package br.com.hazze.cury.marketplace.mappers;

import br.com.hazze.cury.marketplace.dto.request.UserAdminRequestDTO;
import br.com.hazze.cury.marketplace.dto.request.UserClientRequestDTO;
import br.com.hazze.cury.marketplace.dto.response.UserResponseDTO;
import br.com.hazze.cury.marketplace.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "role", expression = "java(br.com.hazze.cury.marketplace.entities.Role.CLIENT)")
    User toEntity(UserClientRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "orders", ignore = true)
    User toEntity(UserAdminRequestDTO dto);

    UserResponseDTO toResponse(User user);

    List<UserResponseDTO> toResponseList(List<User> users);
}
