package com.example.gsmgogo.domain.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {

    USER("USER"),
    PLAYER("PLAYER"),
    LEADER("LEADER");

    private final String role;
}
