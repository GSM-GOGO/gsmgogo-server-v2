package team.gsmgogo.domain.team.service;

import team.gsmgogo.domain.team.controller.dto.request.TeamNomalSaveRequest;

import java.util.List;

public interface TeamNomalSaveService {
    void saveNomalTeam(List<TeamNomalSaveRequest> request);
}
