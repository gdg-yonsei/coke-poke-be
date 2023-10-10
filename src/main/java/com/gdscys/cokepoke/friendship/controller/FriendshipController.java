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

import static java.util.stream.Nodes.collect;

@Controller
@RequestMapping("/friendship")
@RequiredArgsConstructor
public class FriendshipController {

    private final FriendshipService friendshipService;

    // 친구관계 만들기
    @PostMapping("/create")
    public ResponseEntity<Void> createFriendship(@AuthenticationPrincipal Member member,
                                                 @RequestBody @Valid FriendshipRequest request) {
        friendshipService.createFriendship(member, request.getRecipientUsername());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 내 친구관계 전체조회
    @GetMapping("/my")
    public ResponseEntity<List<FriendshipResponse>> findFriendshipsByMember(@AuthenticationPrincipal Member member,
                                                                            @RequestParam(defaultValue = "0") int page) {
        List<FriendshipResponse> responses = friendshipService.findFriendshipsByMember(member, page)
                .stream()
                .map(FriendshipResponse::of)
                .collect(Collectors.toList());
    }


}
