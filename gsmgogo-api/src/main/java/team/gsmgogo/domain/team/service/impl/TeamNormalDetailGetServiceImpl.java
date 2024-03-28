package team.gsmgogo.domain.team.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.gsmgogo.domain.normalteamparticipate.entity.NormalTeamParticipateEntity;
import team.gsmgogo.domain.normalteamparticipate.repository.NormalTeamParticipateJpaRepository;
import team.gsmgogo.domain.team.controller.dto.response.NormalTeamParticipateDto;
import team.gsmgogo.domain.team.controller.dto.response.TeamNormalListResponse;
import team.gsmgogo.domain.team.entity.TeamEntity;
import team.gsmgogo.domain.team.enums.TeamType;
import team.gsmgogo.domain.team.repository.TeamJpaRepository;
import team.gsmgogo.domain.team.service.TeamNormalDetailGetService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamNormalDetailGetServiceImpl implements TeamNormalDetailGetService {
    private final TeamJpaRepository teamJpaRepository;
    private final NormalTeamParticipateJpaRepository normalTeamParticipateJpaRepository;

    @Override
    public TeamNormalListResponse execute(String teamId) {
        List<NormalTeamParticipateEntity> normalTeamParticipateList = normalTeamParticipateJpaRepository.findByTeamTeamId(Long.valueOf(teamId));

        TeamEntity team = teamJpaRepository.findByTeamTypeAndTeamId(TeamType.NORMAL, teamId);
        return new TeamNormalListResponse(
            team.getTeamId(),
            team.getTeamGrade().getRole(),
            team.getTeamClass().getClass().getName(),
            normalTeamParticipateList.stream().map(normalTeamParticipate ->
                new NormalTeamParticipateDto(
                    normalTeamParticipate.getUser().getUserId().toString(),
                    normalTeamParticipate.getUser().getUserName()
                )
            ).toList()
        );
    }
}
