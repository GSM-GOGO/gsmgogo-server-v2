package team.gsmgogo.domain.match.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.gsmgogo.domain.match.controller.dto.response.MatchResponse;
import team.gsmgogo.domain.match.service.MatchService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/match")
public class MatchController {
    private final MatchService matchService;

    @GetMapping
    public ResponseEntity<MatchResponse> getMatches(
        @RequestParam("m") Integer month,
        @RequestParam("d") Integer day
    ){
        return ResponseEntity.ok(matchService.execute(month, day));
    }
}
