package team.gsmgogo.domain.team.service;

import team.gsmgogo.domain.team.controller.dto.request.TeamBadmintonSaveRequest;

public interface TeamFollowService {
    void followTeam(TeamBadmintonSaveRequest request);
}
