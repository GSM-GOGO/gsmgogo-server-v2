package team.gsmgogo.domain.auth.service.impl;

import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.gsmgogo.domain.auth.entity.VerifyCodeRedisEntity;
import team.gsmgogo.domain.auth.repository.VerifyCodeJpaRepository;
import team.gsmgogo.domain.auth.service.MessageSendService;
import team.gsmgogo.domain.user.entity.UserEntity;
import team.gsmgogo.domain.user.repository.UserJpaRepository;
import team.gsmgogo.global.exception.error.ExpectedException;
import team.gsmgogo.global.facade.UserFacade;

import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class MessageSendSendServiceImpl implements MessageSendService {
    private final UserFacade userFacade;
    private final DefaultMessageService messageService;
    private final VerifyCodeJpaRepository verifyCodeJpaRepository;
    private final UserJpaRepository userJpaRepository;

    @Value("${message.send}")
    private String sendNumber;

    @Override
    @Transactional
    public void execute(Long code) {
        UserEntity user = userFacade.getCurrentUser();

        if(user.getVerifyCount() >= 5) throw new ExpectedException("하루에 인증은 5번만 할 수 있습니다.", HttpStatus.NO_CONTENT);

        String generatedCode = generateCode();

        Message message = new Message();
        message.setFrom(sendNumber);
        message.setTo(code.toString());
        message.setText("GSM GOGO v2 [ " + generatedCode + "]를 입력해주세요!");

        messageService.sendOne(new SingleMessageSendingRequest(message));

        VerifyCodeRedisEntity verifyCode = VerifyCodeRedisEntity.builder()
            .userId(user.getUserId())
            .code(generatedCode)
            .expiredAt(600000L)
            .build();

        user.plusCount();

        verifyCodeJpaRepository.save(verifyCode);
        userJpaRepository.save(user);
    }

    private String generateCode(){
        return String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));
    }
}
