package team.gsmgogo.domain.match.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MatchLevelType {

    TRYOUT("TRYOUT"),
    SEMI_FINAL("SEMI_FINAL"),
    FINAL("FINAL");

    private final String level;
}
