package team.gsmgogo.domain.team.service;

import org.springframework.http.ResponseEntity;
import team.gsmgogo.domain.team.controller.dto.response.TeamGetResponse;
import team.gsmgogo.domain.team.enums.TeamType;

public interface TeamGetService {
    TeamGetResponse getTeam(TeamType teamType);
}
