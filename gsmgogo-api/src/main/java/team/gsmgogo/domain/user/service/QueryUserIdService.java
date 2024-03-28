package team.gsmgogo.domain.user.service;

import team.gsmgogo.domain.user.dto.response.UserIdResponse;

public interface QueryUserIdService {
    UserIdResponse queryUserId(Long userId);
}
