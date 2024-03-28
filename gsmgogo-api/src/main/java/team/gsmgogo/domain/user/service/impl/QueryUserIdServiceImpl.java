package team.gsmgogo.domain.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.gsmgogo.domain.user.dto.response.UserIdResponse;
import team.gsmgogo.domain.user.service.QueryUserIdService;
import team.gsmgogo.global.facade.UserFacade;

@Service
@RequiredArgsConstructor
public class QueryUserIdServiceImpl implements QueryUserIdService {

    private final UserFacade userFacade;

    @Override
    public UserIdResponse queryUserId() {
        return new UserIdResponse(userFacade.getCurrentUser().getUserId());
    }
}
