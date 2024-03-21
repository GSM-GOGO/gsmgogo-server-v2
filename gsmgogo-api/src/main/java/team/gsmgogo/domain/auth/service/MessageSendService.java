package team.gsmgogo.domain.auth.service;

public interface MessageSendService {
    void execute(Long code);
    String test(Long code);
}
