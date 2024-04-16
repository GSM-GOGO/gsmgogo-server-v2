package team.gsmgogo.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CalculatePointRequest {
    private Long betPoint;
    private int aScore;
    private int bScore;
    private int betAScore;
    private int betBScore;
    private Long allAPoint;
    private Long allBPoint;
}
