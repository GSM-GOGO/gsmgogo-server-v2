package team.gsmgogo.domain.auth.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthSendCodeRequest {
    @JsonProperty(value = "phone_number")
    private Long phoneNumber;
}
