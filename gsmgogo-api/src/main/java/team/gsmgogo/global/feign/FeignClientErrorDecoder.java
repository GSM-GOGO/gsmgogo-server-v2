package team.gsmgogo.global.feign;

import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import team.gsmgogo.global.exception.error.ExpectedException;

public class FeignClientErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) throws FeignException {

        if(response.status() >= 400) {
            throw new ExpectedException("Feign Client 예외가 발생했습니다. " + response.status(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return FeignException.errorStatus(methodKey, response);
    }
}
