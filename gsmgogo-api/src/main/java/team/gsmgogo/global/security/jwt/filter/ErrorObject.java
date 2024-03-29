package team.gsmgogo.global.security.jwt.filter;

import lombok.Getter;

@Getter
public class ErrorObject {
    private final String message;

    public ErrorObject(String message){
        this.message = message;
    }
}
