package team.gsmgogo.global.jwt.filter;

import lombok.Getter;

@Getter
public class ErrorObject {
    private final String message;

    public ErrorObject(String message){
        this.message = message;
    }
}
