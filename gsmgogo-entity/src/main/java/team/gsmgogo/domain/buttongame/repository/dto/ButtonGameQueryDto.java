package team.gsmgogo.domain.buttongame.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team.gsmgogo.domain.buttongame.enums.ButtonType;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ButtonGameQueryDto {
    private ButtonType buttonType;
    private LocalDateTime date;
    private Boolean isActive;
    private List<Integer> result;
}

