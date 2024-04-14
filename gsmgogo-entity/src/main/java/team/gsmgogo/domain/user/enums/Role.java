package team.gsmgogo.domain.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {

    USER("USER"),
    ROOT("ROOT"),
    LEADER("LEADER");

    private final String role;
}
