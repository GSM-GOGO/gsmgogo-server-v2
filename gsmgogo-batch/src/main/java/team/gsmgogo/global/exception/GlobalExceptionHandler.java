package team.gsmgogo.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import team.gsmgogo.global.exception.model.ExceptionResponseEntity;

@Slf4j
@EnableWebMvc
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponseEntity> unExpectedException(RuntimeException ex) {
        log.error("알 수 없는 예외가 발생했어요 : ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .body(new ExceptionResponseEntity("서버 에러가 발생했습니다."));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ExceptionResponseEntity> noHandlerFoundException(NoHandlerFoundException ex) {
        log.warn("엔드포인트를 찾을 수 없어요 : {}", ex.getMessage());
        log.trace("엔드포인트를 찾을 수 없어요 Details : ", ex);
        return ResponseEntity.status(ex.getStatusCode())
                .body(new ExceptionResponseEntity(HttpStatus.NOT_FOUND.getReasonPhrase()));
    }

}