package team.gsmgogo.domain.team.service;

import team.gsmgogo.domain.team.controller.dto.response.TeamListResponse;
import team.gsmgogo.domain.team.enums.TeamType;

public interface TeamGetService {
    TeamListResponse getTeam(TeamType teamType);
}
