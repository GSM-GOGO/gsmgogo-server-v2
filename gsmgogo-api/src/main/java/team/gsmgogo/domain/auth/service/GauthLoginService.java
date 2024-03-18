package team.gsmgogo.domain.auth.service;

import java.net.URISyntaxException;

public interface GauthLoginService {
    void execute(String code) throws URISyntaxException;
}
