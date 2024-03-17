package com.example.gsmgogoentity.domain.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {

    USER("ROLE_USER"),
    PLAYER("ROLE_PLAYER"),
    LEADER("LEADER");

    private final String role;
}
