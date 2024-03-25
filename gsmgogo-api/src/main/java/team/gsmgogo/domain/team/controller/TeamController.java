package team.gsmgogo.domain.team.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.gsmgogo.domain.team.controller.dto.request.TeamDeleteRequest;
import team.gsmgogo.domain.team.controller.dto.request.TeamNomalSaveRequest;
import team.gsmgogo.domain.team.controller.dto.request.TeamSaveRequest;
import team.gsmgogo.domain.team.service.TeamDeleteService;
import team.gsmgogo.domain.team.service.TeamNomalSaveService;
import team.gsmgogo.domain.team.service.TeamSaveService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {

    private final TeamSaveService teamSaveService;
    private final TeamNomalSaveService teamNomalSaveService;
    private final TeamDeleteService teamDeleteService;

    @PostMapping
    public ResponseEntity<Void> saveTeam(@RequestBody @Valid TeamSaveRequest request) {
        teamSaveService.saveTeam(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/nomal")
    public ResponseEntity<Void> saveNomalTeam(@RequestBody @Valid List<TeamNomalSaveRequest> request) {
        teamNomalSaveService.saveNomalTeam(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTeam(@RequestBody @Valid TeamDeleteRequest request) {
        teamDeleteService.deleteTeam(request);
        return ResponseEntity.ok().build();
    }

}
