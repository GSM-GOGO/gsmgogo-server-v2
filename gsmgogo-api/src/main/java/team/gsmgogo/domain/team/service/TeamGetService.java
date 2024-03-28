package team.gsmgogo.domain.team.service;

import team.gsmgogo.domain.team.controller.dto.response.TeamListResponse;
import team.gsmgogo.domain.team.enums.TeamType;

import java.util.List;

public interface TeamGetService {
    List<TeamListResponse> getTeam(String type);
}
