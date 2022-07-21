package com.app.utils.dto;

import com.app.persistence.entities.UserRole;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserDto implements Serializable {
    private final long ID;
    private final String firstName;
    private final String lastName;
    private final String codeName;
    private final String password;
    private final List<UserRole> roles;
}
