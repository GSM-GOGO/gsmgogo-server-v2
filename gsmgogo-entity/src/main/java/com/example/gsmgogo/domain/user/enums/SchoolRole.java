package com.example.gsmgogo.domain.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SchoolRole {

    STUDENT("STUDENT"),
    TEACHER("TEACHER");

    private final String role;
}
