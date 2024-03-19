package team.gsmgogo.global.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import team.gsmgogo.global.config.GauthFeignConfig;
import team.gsmgogo.global.feign.dto.GauthTokenDto;
import team.gsmgogo.global.feign.dto.GauthTokenRequest;

@FeignClient(name = "GauthToken", configuration = GauthFeignConfig.class, url = "https://server.gauth.co.kr/oauth/token")
public interface GauthToken {
    @PostMapping
    GauthTokenDto getToken(@RequestBody GauthTokenRequest gauthTokenRequest);
}