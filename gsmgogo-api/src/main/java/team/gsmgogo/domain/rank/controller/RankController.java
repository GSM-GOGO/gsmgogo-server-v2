package team.gsmgogo.domain.rank.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.gsmgogo.domain.rank.controller.dto.response.RankListResponse;
import team.gsmgogo.domain.rank.service.QueryUserRankService;

import java.util.List;

@RestController
@RequestMapping("/rank")
@RequiredArgsConstructor
public class RankController {

    private final QueryUserRankService queryUserRankService;

    @GetMapping
    public ResponseEntity<List<RankListResponse>> queryRankList() {
        return ResponseEntity.ok(queryUserRankService.queryUserRank());
    }

}
