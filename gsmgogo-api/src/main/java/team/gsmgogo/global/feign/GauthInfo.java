package team.gsmgogo.global.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import team.gsmgogo.global.config.GauthFeignConfig;
import team.gsmgogo.global.feign.dto.GauthUserDto;

@FeignClient(name = "GauthInfo", configuration = GauthFeignConfig.class, url = "https://port-0-gauth-resource-server-71t02clq411q18.sel4.cloudtype.app/user")
public interface GauthInfo {
    @GetMapping
    GauthUserDto getInfo(@RequestHeader("Authorization") String accessToken);
}
