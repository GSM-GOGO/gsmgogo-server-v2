package team.gsmgogo.domain.team.service;

import team.gsmgogo.domain.team.controller.dto.request.TeamDeleteRequest;

public interface TeamDeleteService {
    void deleteTeam(TeamDeleteRequest request);
}
