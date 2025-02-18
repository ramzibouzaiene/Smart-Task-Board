package com.smarttaskboard.app.mapper;

import com.smarttaskboard.app.dto.RoleDto;
import com.smarttaskboard.app.model.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toEntity(RoleDto roleDto);

    RoleDto toDto(Role role);
}
