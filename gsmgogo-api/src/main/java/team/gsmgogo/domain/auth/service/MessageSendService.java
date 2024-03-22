package team.gsmgogo.domain.auth.service;

public interface MessageSendService {
    void execute(String phoneNumber);
    String test(String phoneNumber);
}
