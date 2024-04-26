package team.gsmgogo.global.exception;

import org.springframework.http.HttpStatus;


public class TokenException extends RuntimeException {
    private final HttpStatus statusCode;

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public TokenException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public TokenException(HttpStatus statusCode) {
        this(statusCode.getReasonPhrase(), statusCode);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

}
