package team.gsmgogo.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.gsmgogo.domain.user.dto.response.UserInfoResponse;
import team.gsmgogo.domain.user.service.QueryUserInfoService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final QueryUserInfoService queryUserInfoService;

    @GetMapping
    public ResponseEntity<List<UserInfoResponse>> queryUser(@RequestParam(name = "name") String name) {
        return ResponseEntity.ok(queryUserInfoService.queryUserInfo(name));
    }

}