package team.gsmgogo.global.config;

import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageConfig {
    @Value("${message.apiKey}")
    private String apiKey;

    @Value("${message.apiSecretKey}")
    private String apiSecretKey;

    @Bean
    public DefaultMessageService defaultMessageService() {
        return new DefaultMessageService(apiKey, apiSecretKey, "https://api.coolsms.co.kr");
    }
}
