package team.gsmgogo.domain.team.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.gsmgogo.domain.team.controller.dto.request.TeamDeleteRequest;
import team.gsmgogo.domain.team.controller.dto.request.TeamSaveRequest;
import team.gsmgogo.domain.team.service.TeamDeleteService;
import team.gsmgogo.domain.team.service.TeamSaveService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {

    private final TeamSaveService teamSaveService;
    private final TeamDeleteService teamDeleteService;

    @PostMapping
    public ResponseEntity<Void> saveTeam(@RequestBody TeamSaveRequest request) {
        teamSaveService.saveTeam(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTeam(@RequestBody TeamDeleteRequest request) {
        teamDeleteService.deleteTeam(request);
        return ResponseEntity.ok().build();
    }

}
