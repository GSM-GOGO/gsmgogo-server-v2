package team.gsmgogo.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import team.gsmgogo.domain.buttongame.repository.ButtonGameRepository;
import team.gsmgogo.domain.buttongameparticipate.repository.ButtonGameParticipateQueryDslRepository;

@Configuration
@RequiredArgsConstructor
public class CalculateButtonGameJob {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final ButtonGameRepository buttonGameRepository;
    private final ButtonGameParticipateQueryDslRepository buttonGameParticipateQueryDslRepository;

    // 현재 진행중인 버튼 게임 조회
    // 해당 버튼 게임의 참여자들의 수를 타입 별로 조회

    // 가장 인원이 적은 버튼을 선별 <- 가장 버튼의 타입 필요

    // 해당 버튼을 선택한 유저들에게 200만원을 분배 <- 해당 타입의 유저들의 list가 필요

    // 버튼 게임의 win_type에 해당 버튼 타입을 등록
    // 버튼 게임읠 Is_active를 false로 변경
    // 새로운 버튼게임을 등록

    @Bean(name = "calculateButtonGameJob")
    public Job calculateButtonGameJob(){
        return new JobBuilder("calculate-button-game-job", jobRepository)
                .build();
    }

}
