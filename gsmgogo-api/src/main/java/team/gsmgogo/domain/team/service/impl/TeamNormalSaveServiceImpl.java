package team.gsmgogo.domain.team.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.gsmgogo.domain.normalteamparticipate.entity.NormalTeamParticipateEntity;
import team.gsmgogo.domain.normalteamparticipate.enums.NormalTeamType;
import team.gsmgogo.domain.normalteamparticipate.repository.NormalTeamParticipateJpaRepository;
import team.gsmgogo.domain.team.controller.dto.request.TeamNormalSaveRequest;
import team.gsmgogo.domain.team.controller.dto.response.TeamClassType;
import team.gsmgogo.domain.team.entity.TeamEntity;
import team.gsmgogo.domain.team.enums.TeamType;
import team.gsmgogo.domain.team.repository.TeamJpaRepository;
import team.gsmgogo.domain.team.service.TeamNormalSaveService;
import team.gsmgogo.domain.user.entity.UserEntity;
import team.gsmgogo.domain.user.enums.ClassEnum;
import team.gsmgogo.domain.user.enums.Gender;
import team.gsmgogo.domain.user.repository.UserJpaRepository;
import team.gsmgogo.global.exception.error.ExpectedException;
import team.gsmgogo.global.facade.UserFacade;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

// TODO
//      TOSS_RUN 이어달리기: 남자 4명, 여자 2명 = 총 6명
//      MISSION_RUN 미션달리기: 6명
//      TUG_OF_WAR 줄다리기: 30명
//      FREE_THROW 자유투 릴레이: 30명
//      GROUP_ROPE_JUMP 단체 줄넘기: 10명
//      CROSS_ROPE_JUMP 8자 줄넘기: 20명

@Service
@RequiredArgsConstructor
public class TeamNormalSaveServiceImpl implements TeamNormalSaveService {

    private final TeamJpaRepository teamJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final NormalTeamParticipateJpaRepository normalTeamParticipateJpaRepository;
    private final UserFacade userFacade;

    @Override
    @Transactional
    public void saveNormalTeam(List<TeamNormalSaveRequest> request) {
        UserEntity currentUser = userFacade.getCurrentUser();

        if (teamJpaRepository.existsByAuthorAndTeamType
                (currentUser, TeamType.NORMAL))
            throw new ExpectedException("이미 등록된 팀이 있습니다.", HttpStatus.BAD_REQUEST);

        Set<Long> chackDublicate = new HashSet<>();
        request.forEach(
                participate -> {
                    if (!chackDublicate.add(participate.getUserId())) {
                        throw new ExpectedException("참가 인원이 중복되서는 안됩니다.", HttpStatus.BAD_REQUEST);
                    }
                }
        );

        Map<NormalTeamType, Integer> maleCount = new HashMap<>();
        Map<NormalTeamType, Integer> femaleCount = new HashMap<>();

        request.stream().map(user -> {
            UserEntity findUser = userJpaRepository.findByUserId(user.getUserId())
                    .orElseThrow(() -> new ExpectedException("유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

            user.getTeamTypes().forEach(
                type -> {
                    if (findUser.getGender().equals(Gender.MALE)) {
                        maleCount.put(type, maleCount.getOrDefault(type, 0) + 1);

                    } else if (findUser.getGender().equals(Gender.FEMALE)) {
                        femaleCount.put(type, femaleCount.getOrDefault(type, 0) + 1);
                    }
                }
            );

            if (
                    findUser.getUserGrade() != currentUser.getUserGrade() ||
                    toTeamClassType(findUser.getUserClass()) != toTeamClassType(currentUser.getUserClass())
            ) {
                throw new ExpectedException("참가 인원은 같은 학년, 과의 학생만 가능합니다.", HttpStatus.BAD_REQUEST);
            }

            return findUser;
        }).toList();

        if (maleCount.getOrDefault(NormalTeamType.TOSS_RUN, 0) != 4
                || femaleCount.getOrDefault(NormalTeamType.TOSS_RUN, 0) != 2) {
            throw new ExpectedException("이어달리기는 남학생 4명, 여학생 2명민 참여 가능합니다.", HttpStatus.BAD_REQUEST);
        }

        if ((maleCount.getOrDefault(NormalTeamType.MISSION_RUN, 0) +
                femaleCount.getOrDefault(NormalTeamType.MISSION_RUN, 0)) != 6)
            throw new ExpectedException("미션달리기는 6명만 참여 가능합니다.", HttpStatus.BAD_REQUEST);

        if ((maleCount.getOrDefault(NormalTeamType.TUG_OF_WAR, 0) +
                femaleCount.getOrDefault(NormalTeamType.TUG_OF_WAR, 0)) != 30)
            throw new ExpectedException("줄다리기는 30명만 참여 가능합니다.", HttpStatus.BAD_REQUEST);

        if ((maleCount.getOrDefault(NormalTeamType.FREE_THROW, 0) +
                femaleCount.getOrDefault(NormalTeamType.FREE_THROW, 0))!= 30)
            throw new ExpectedException("자유투 릴레이는 30명만 참여 가능합니다.", HttpStatus.BAD_REQUEST);

        if ((maleCount.getOrDefault(NormalTeamType.GROUP_ROPE_JUMP, 0) +
                femaleCount.getOrDefault(NormalTeamType.GROUP_ROPE_JUMP, 0)) != 10)
            throw new ExpectedException("단체 줄넘기는 10명만 참여 가능합니다.", HttpStatus.BAD_REQUEST);

        if ((maleCount.getOrDefault(NormalTeamType.CROSS_ROPE_JUMP, 0) +
                femaleCount.getOrDefault(NormalTeamType.CROSS_ROPE_JUMP, 0)) != 20)
            throw new ExpectedException("8자 줄넘기는 20명만 참여 가능합니다.", HttpStatus.BAD_REQUEST);

        TeamEntity newTeam = TeamEntity.builder()
                .author(currentUser)
                .teamClass(currentUser.getUserClass())
                .teamGrade(currentUser.getUserGrade())
                .teamType(TeamType.NORMAL)
                .build();

        TeamEntity savedTeam = teamJpaRepository.save(newTeam);

        List<NormalTeamParticipateEntity> normalTeamParticipateEntities = request.stream().flatMap(
                participate -> participate.getTeamTypes().stream().map(team ->
                        NormalTeamParticipateEntity.builder()
                                .team(savedTeam)
                                .user(userJpaRepository.findByUserId(participate.getUserId())
                                        .orElseThrow(() -> new ExpectedException("유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND)))
                                .normalTeamType(team).build())).toList();

        normalTeamParticipateJpaRepository.saveAll(normalTeamParticipateEntities);
    }

    private TeamClassType toTeamClassType(ClassEnum classEnum) {
        if (classEnum == ClassEnum.ONE || classEnum == ClassEnum.TWO)
            return TeamClassType.SW;
        else
            return TeamClassType.EB;
    }

}

