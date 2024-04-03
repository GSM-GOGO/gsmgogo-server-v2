package team.gsmgogo.domain.user.service;

import team.gsmgogo.domain.user.dto.response.UserInfoResponse;

import java.util.List;

public interface QueryUserListService {
    List<UserInfoResponse> queryUserList(String type);
}
