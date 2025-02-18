package com.smarttaskboard.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String password;
    private Set<RoleDto> roles;
    private List<ProjectDto> ownedProjects;
    private List<ProjectDto> projects;
}
