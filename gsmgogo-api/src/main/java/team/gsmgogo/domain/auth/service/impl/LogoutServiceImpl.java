package team.gsmgogo.domain.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.gsmgogo.domain.auth.service.LogoutService;

@Service
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutService {


    @Override
    @Transactional
    public void logout() {

    }
}
