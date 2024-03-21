package team.gsmgogo.domain.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IsVerify {
    Enabled("활성화"),
    Disabled("비활성화")
    ;

    private final String description;
}
