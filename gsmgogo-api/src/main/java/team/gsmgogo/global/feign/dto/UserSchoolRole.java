package team.gsmgogo.global.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserSchoolRole {

    ROLE_STUDENT("ROLE_STUDENT"),
    ROLE_TEACHER("ROLE_TEACHER"),
    ROLE_GRADUATE("ROLE_GRADUATE");

    private final String role;
}
