package team.gsmgogo.domain.team.service;

import team.gsmgogo.domain.team.controller.dto.request.TeamFollowRequest;

public interface TeamFollowService {
    void followTeam(TeamFollowRequest request);
}
