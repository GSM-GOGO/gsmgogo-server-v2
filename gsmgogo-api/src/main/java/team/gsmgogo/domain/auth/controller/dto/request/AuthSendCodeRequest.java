package team.gsmgogo.domain.auth.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthSendCodeRequest {
    @JsonProperty(value = "phone_number")
    private Long phoneNumber;
}
