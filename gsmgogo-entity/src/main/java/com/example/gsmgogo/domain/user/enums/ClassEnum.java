package com.example.gsmgogo.domain.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ClassEnum {

    ONE("ONE"),
    TWO("TWO"),
    THREE("THREE"),
    FOUR("FOUR");

    private final String role;
}
