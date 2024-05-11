package team.gsmgogo.domain.buttongameparticipate.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team.gsmgogo.domain.buttongame.enums.ButtonType;
import team.gsmgogo.domain.user.entity.UserEntity;

import java.util.List;

@Getter
@AllArgsConstructor
public class ButtonGameResultDto {
    private ButtonType winType;
    private List<UserEntity> winUserList;
}
