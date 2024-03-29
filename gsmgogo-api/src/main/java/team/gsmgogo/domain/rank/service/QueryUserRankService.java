package team.gsmgogo.domain.rank.service;

import team.gsmgogo.domain.rank.controller.dto.response.RankListResponse;

import java.util.List;

public interface QueryUserRankService {
    List<RankListResponse> queryUserRank();
}
