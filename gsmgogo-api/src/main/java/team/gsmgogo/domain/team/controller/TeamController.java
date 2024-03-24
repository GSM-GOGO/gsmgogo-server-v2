package team.gsmgogo.domain.team.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.gsmgogo.domain.team.controller.dto.request.TeamSaveRequest;
import team.gsmgogo.domain.team.service.TeamSaveService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {

    private final TeamSaveService teamSaveService;

    @PostMapping
    public ResponseEntity<Void> saveTeam(@RequestBody TeamSaveRequest request) {
        teamSaveService.saveTeam(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
