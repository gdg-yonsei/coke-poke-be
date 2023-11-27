package gdsc.cokepoke.controller;

import gdsc.cokepoke.dto.friend.CreateFriendRequestDto;
import gdsc.cokepoke.dto.friend.CreateFriendResponseDto;
import gdsc.cokepoke.dto.friend.GetFriendRequestDto;
import gdsc.cokepoke.dto.friend.GetFriendResponseDto;
import gdsc.cokepoke.service.FriendRelationshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FriendRelationshipController {
    private final FriendRelationshipService friendRelationshipService;

    // 친구 관계 생성
    @PostMapping("/friend")
    public ResponseEntity<CreateFriendResponseDto> createFriendRelationship(@RequestBody CreateFriendRequestDto createFriendRequestDto) {
        return ResponseEntity.ok(friendRelationshipService.createFriendRelationship(createFriendRequestDto));
    }

    // 친구 관계 조회 ([팔로잉] 내가 팔로잉 중인 친구)
    @GetMapping("/following")
    public ResponseEntity<List<GetFriendResponseDto>> getFollowing(@RequestBody GetFriendRequestDto getFriendRequestDto) {
        return ResponseEntity.ok(friendRelationshipService.getFollowing(getFriendRequestDto));
    }

    // 친구 관계 조회 ([팔로워] 나를 팔로우 하고 있는 친구)
    @GetMapping("/follower")
    public ResponseEntity<List<GetFriendResponseDto>> getFollower(@RequestBody GetFriendRequestDto getFriendRequestDto) {
        return ResponseEntity.ok(friendRelationshipService.getFollower(getFriendRequestDto));
    }
}
