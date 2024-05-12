package team.gsmgogo.domain.game.controller.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.gsmgogo.domain.buttongame.enums.ButtonType;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ButtonGameResponse {
    private ButtonType buttonType;
    private LocalDateTime date;
    private Boolean isActive;
    private List<Integer> results;
    private Boolean isWin;
    private ButtonType winType;
    private Integer earnedPoint;
}
