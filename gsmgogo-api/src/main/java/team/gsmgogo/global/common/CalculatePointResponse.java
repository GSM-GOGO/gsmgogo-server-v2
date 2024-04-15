package team.gsmgogo.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CalculatePointResponse {
    private Long earnedPoint = null;
    private Long losePoint = null;
}
