package team.gsmgogo.domain.team.service;

import team.gsmgogo.domain.team.controller.dto.request.TeamNormalSaveRequest;

import java.util.List;

public interface TeamNormalSaveService {
    void saveNormalTeam(List<TeamNormalSaveRequest> request);
}
