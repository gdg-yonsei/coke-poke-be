package com.gdscys.cokepoke.friendship.controller;

import com.gdscys.cokepoke.friendship.dto.FriendshipRequest;
import com.gdscys.cokepoke.friendship.dto.FriendshipResponse;
import com.gdscys.cokepoke.friendship.service.FriendshipService;
import com.gdscys.cokepoke.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.gdscys.cokepoke.auth.SecurityUtil.getLoginUsername;


@Controller
@RequestMapping("/friendship")
@RequiredArgsConstructor
public class FriendshipController {

    private final FriendshipService friendshipService;

    // 친구관계 만들기
    @PostMapping("/create")
    public ResponseEntity<Void> createFriendship(@RequestBody @Valid FriendshipRequest request) {
        String username = getLoginUsername();
        friendshipService.createFriendship(username, request.getToUsername(), request.getMyAddress());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 내 친구관계 전체조회
    @GetMapping("/my")
    public ResponseEntity<List<FriendshipResponse>> findFriendshipsByMember(@RequestParam(defaultValue = "0") int page) {
        String username = getLoginUsername();
        List<FriendshipResponse> responses = friendshipService.getFriendshipsByMember(username, page)
                .stream()
                .map(FriendshipResponse::of)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }


}
