package team.gsmgogo.domain.team.service;

import team.gsmgogo.domain.team.controller.dto.request.TeamSaveRequest;

public interface TeamSaveService {
    void saveTeam(TeamSaveRequest request);
}
