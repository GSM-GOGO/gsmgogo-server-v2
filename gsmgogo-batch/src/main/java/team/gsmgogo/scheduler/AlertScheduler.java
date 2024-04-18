package team.gsmgogo.scheduler;

import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import team.gsmgogo.domain.match.entity.MatchEntity;
import team.gsmgogo.domain.match.repository.MatchQueryDslRepository;
import team.gsmgogo.job.AlertJob;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AlertScheduler {
    private final Scheduler scheduler;
    private final MatchQueryDslRepository matchQueryDslRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void start(){
        LocalDate today = LocalDate.now();
        List<MatchEntity> matches = matchQueryDslRepository.findByMonthAndDay(
            today.getMonthValue(),
            today.getDayOfMonth()
        );

        matches.forEach(match -> {
            try {
                LocalDateTime beforeMatch = match.getStartAt().minusMinutes(10);

                JobDetailImpl detail1 = new JobDetailImpl();
                detail1.setName("alert-detail");
                detail1.setGroup("alert");
                detail1.setJobClass(AlertJob.class);

                CronTriggerImpl trigger1 = new CronTriggerImpl();
                trigger1.setName("alert-trigger");
                trigger1.setGroup("alert");
                trigger1.setCronExpression("0 %d %d %d %d ? %d".formatted(
                    beforeMatch.getMinute(),
                    beforeMatch.getHour(),
                    beforeMatch.getDayOfMonth(),
                    beforeMatch.getMonthValue(),
                    beforeMatch.getYear()
                ));

                scheduler.scheduleJob(detail1, trigger1);
            } catch (SchedulerException | ParseException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
