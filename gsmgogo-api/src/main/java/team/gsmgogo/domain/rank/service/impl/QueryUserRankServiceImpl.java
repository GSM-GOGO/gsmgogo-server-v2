package team.gsmgogo.domain.rank.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.gsmgogo.domain.rank.controller.dto.response.RankListResponse;
import team.gsmgogo.domain.rank.service.QueryUserRankService;
import team.gsmgogo.domain.user.repository.UserJpaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryUserRankServiceImpl implements QueryUserRankService {

    private final UserJpaRepository userJpaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<RankListResponse> queryUserRank() {
        return userJpaRepository.findAllByOrderByPointDesc().stream().map(user ->
                RankListResponse.builder()
                        .userId(user.getUserId())
                        .userName(user.getUserName())
                        .userGrade(user.getUserGrade())
                        .userClass(user.getUserClass())
                        .userNum(user.getUserNum())
                        .userPoint(user.getPoint()).build()
                ).toList();
    }
}
