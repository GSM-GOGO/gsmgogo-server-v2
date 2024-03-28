package team.gsmgogo.domain.user.service;

import team.gsmgogo.domain.user.dto.response.UserInfoResponse;

public interface QueryUserInfoService {
    UserInfoResponse queryUserInfo(String name);
}
