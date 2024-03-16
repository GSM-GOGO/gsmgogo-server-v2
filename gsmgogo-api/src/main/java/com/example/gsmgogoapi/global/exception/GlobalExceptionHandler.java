package com.example.gsmgogoapi.global.exception;

import com.example.gsmgogoapi.global.exception.error.ExpectedException;
import com.example.gsmgogoapi.global.exception.model.ExceptionResponseEntity;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.HashMap;
import java.util.Map;
@Slf4j
@EnableWebMvc
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExpectedException.class)
    private ResponseEntity<ExceptionResponseEntity> expectedException(ExpectedException ex) {
        log.warn("예상된 예외가 발생했어요 : {} ", ex.getMessage());
        log.trace("예상된 예외가 발생했어요 Details : ", ex);
        return ResponseEntity.status(ex.getStatusCode().value())
                .body(ExceptionResponseEntity.of(ex));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ExceptionResponseEntity> validationException(MethodArgumentNotValidException ex) {
        log.warn("유효성 검증을 실패했어요 : {}", ex.getMessage());
        log.trace("유효성 검증을 실패했어요 Details : ", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(new ExceptionResponseEntity(methodArgumentNotValidExceptionToJson(ex)));
    }

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

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ExceptionResponseEntity> maxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        log.warn("파일어 너무 커요 : {}", ex.getMessage());
        log.trace("파일어 너무 커요 Details : ", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(new ExceptionResponseEntity("파일어 너무 커요, 최대 파일 사이즈에요 : " + ex.getMaxUploadSize()));
    }

    private static String methodArgumentNotValidExceptionToJson(MethodArgumentNotValidException ex) {
        Map<String, Object> globalResults = new HashMap<>();
        Map<String, String> fieldResults = new HashMap<>();

        ex.getBindingResult().getGlobalErrors().forEach(error -> {
            globalResults.put(ex.getBindingResult().getObjectName(), error.getDefaultMessage());
        });
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            fieldResults.put(error.getField(), error.getDefaultMessage());
        });
        globalResults.put(ex.getBindingResult().getObjectName(), fieldResults);

        return new JSONObject(globalResults).toString().replace("\"", "'");
    }

}