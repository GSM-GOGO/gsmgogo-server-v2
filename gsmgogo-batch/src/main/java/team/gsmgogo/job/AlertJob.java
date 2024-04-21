package team.gsmgogo.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import team.gsmgogo.domain.match.entity.MatchEntity;
import team.gsmgogo.domain.match.enums.MatchLevelType;
import team.gsmgogo.domain.match.repository.MatchJpaRepository;
import team.gsmgogo.domain.team.entity.TeamEntity;
import team.gsmgogo.domain.user.entity.UserEntity;
import team.gsmgogo.domain.user.repository.UserQueryDslRepository;

import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AlertJob implements Job {
    private final MatchJpaRepository matchJpaRepository;
    private final UserQueryDslRepository userQueryDslRepository;
    private final DefaultMessageService messageService;

    @Value("${message.send}")
    private String sendNumber;

    @Override
    public void execute(JobExecutionContext context) {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();

        Long matchId = jobDataMap.getLong("matchId");
        MatchEntity match = matchJpaRepository.findByMatchId(matchId)
            .orElseThrow(() -> new RuntimeException("배치에서 해당 매치를 찾을 수 없습니다."));

        String matchLevel;
        if(match.getMatchLevel() == MatchLevelType.TRYOUT){
            matchLevel = "예선";
        } else if(match.getMatchLevel() == MatchLevelType.SEMI_FINAL){
            matchLevel = "준결승";
        } else {
            matchLevel = "결승";
        }

        TeamEntity teamA = match.getTeamA();
        TeamEntity teamB = match.getTeamB();

        List<UserEntity> followAUsers = userQueryDslRepository.findByFollowsTeam(teamA);
        followAUsers.
            stream().filter(user -> user.getPhoneNumber() != null)
                .forEach(user -> {
            Message message = new Message();
            message.setFrom(sendNumber);
            message.setTo(user.getPhoneNumber());
            message.setText(
                """
                GSM GOGO 경기 알림
                                                    
                곧 %s팀 VS %s팀 %s 경기가 시작됩니다!
                %s팀의 승리를 위해 힘찬 응원 부탁드립니다!
                                                    
                잠시 후 10분 후 경기가 시작됩니다. 아직 배팅에 참여하지 않았다면 서비스에 접속하여 배팅을 진행해주세요!
                                                    
                https://gsmgogo.kr
                """.formatted(teamA.getTeamName(), teamB.getTeamName(), matchLevel, teamA.getTeamName())
            );
            messageService.sendOne(new SingleMessageSendingRequest(message));
        });

        List<UserEntity> followBUsers = userQueryDslRepository.findByFollowsTeam(teamB);
        followBUsers.
            stream().filter(user -> user.getPhoneNumber() != null)
                .forEach(user -> {
            Message message = new Message();
            message.setFrom(sendNumber);
            message.setTo(user.getPhoneNumber());
            message.setText(
                """
                GSM GOGO 경기 알림
                                                    
                곧 %s팀 VS %s팀 %s 경기가 시작됩니다!
                %s팀의 승리를 위해 힘찬 응원 부탁드립니다!
                                                    
                잠시 후 10분 후 경기가 시작됩니다. 아직 배팅에 참여하지 않았다면 서비스에 접속하여 배팅을 진행해주세요!
                                                    
                https://gsmgogo.kr
                """.formatted(teamB.getTeamName(), teamA.getTeamName(), matchLevel, teamB.getTeamName())
            );
            messageService.sendOne(new SingleMessageSendingRequest(message));
        });
    }
}
