package team.gsmgogo.global.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
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
