package team.gsmgogo.domain.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.gsmgogo.domain.user.dto.response.UserPointResponse;
import team.gsmgogo.domain.user.service.QueryUserPointService;
import team.gsmgogo.global.facade.UserFacade;

@Service
@RequiredArgsConstructor
public class QueryUserPointServiceImpl implements QueryUserPointService {

    private final UserFacade userFacade;

    @Override
    public UserPointResponse queryUserPoint() {
        return new UserPointResponse(userFacade.getCurrentUser().getPoint());
    }
}
