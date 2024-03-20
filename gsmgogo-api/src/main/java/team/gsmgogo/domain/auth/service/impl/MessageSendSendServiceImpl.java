package team.gsmgogo.domain.auth.service.impl;

import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.gsmgogo.domain.auth.entity.VerifyCodeRedisEntity;
import team.gsmgogo.domain.auth.repository.VerifyCodeJpaRepository;
import team.gsmgogo.domain.auth.service.MessageSendService;
import team.gsmgogo.global.security.SecurityAdapter;

import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class MessageSendSendServiceImpl implements MessageSendService {
    private final SecurityAdapter securityAdapter;
    private final DefaultMessageService messageService;
    private final VerifyCodeJpaRepository verifyCodeJpaRepository;

    @Value("${message.send}")
    private String sendNumber;

    @Override
    @Transactional
    public void execute(Long code) {
        Long id = securityAdapter.getCurrentUserId();
        String generatedCode = generateCode();

        Message message = new Message();
        message.setFrom(sendNumber);
        message.setTo(code.toString());
        message.setText("GOGO 인증번호: " + generatedCode);

        messageService.sendOne(new SingleMessageSendingRequest(message));

        VerifyCodeRedisEntity verifyCode = VerifyCodeRedisEntity.builder()
            .userId(id)
            .code(generatedCode)
            .expiredAt(600000L)
            .build();

        verifyCodeJpaRepository.save(verifyCode);

    }

    private String generateCode(){
        return String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));
    }
}
