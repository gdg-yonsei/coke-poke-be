package gdsc.cokepoke.service;

import gdsc.cokepoke.dto.friend.CreateFriendRequestDto;
import gdsc.cokepoke.dto.friend.CreateFriendResponseDto;
import gdsc.cokepoke.dto.friend.GetFriendResponseDto;
import gdsc.cokepoke.entity.FriendRelationship;
import gdsc.cokepoke.repository.FriendRelationshipRepository;
import gdsc.cokepoke.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendRelationshipService {
    private final FriendRelationshipRepository friendRelationshipRepository;
    private final MemberRepository memberRepository;

    public CreateFriendResponseDto createFriendRelationship(CreateFriendRequestDto friendRequestDto) {
        // Exception 1 : 존재하지 않는 Receiver Email인 경우
        if (!memberRepository.existsByEmail(friendRequestDto.getReceiverEmail())) {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다. 이메일 주소를 다시 확인해주세요.");
        }
        // Exception 2 : 이미 친구 관계인 경우
        if (friendRelationshipRepository.existsBySenderEmailAndReceiverEmail(friendRequestDto.getSenderEmail(), friendRequestDto.getReceiverEmail())) {
            throw new IllegalArgumentException("이미 친구 관계가 존재합니다.");
        }
        // Exception 3 : 자기 자신에게 친구 요청을 보낸 경우
        if (friendRequestDto.getSenderEmail().equals(friendRequestDto.getReceiverEmail())) {
            throw new IllegalArgumentException("자기 자신에게 친구 요청을 보낼 수 없습니다.");
        }
        // 친구 관계 생성
        FriendRelationship friendRelationship = FriendRelationship.builder()
                .senderEmail(friendRequestDto.getSenderEmail())
                .receiverEmail(friendRequestDto.getReceiverEmail())
                .build();
        return CreateFriendResponseDto.of(friendRelationshipRepository.save(friendRelationship));
    }

    public List<GetFriendResponseDto> getFollowing(String email) {
        // 친구 관계 조회 ([팔로잉] 내가 팔로잉 중인 친구)
        List<FriendRelationship> friendRelationships = friendRelationshipRepository.findBySenderEmail(email);
        List<String> friendEmails = friendRelationships.stream()
                .map(FriendRelationship::getReceiverEmail)
                .toList();
        return friendEmails.stream()
                .map(GetFriendResponseDto::of)
                .collect(Collectors.toList());
    }

    public List<GetFriendResponseDto> getFollower(String email) {
        // 친구 관계 조회 ([팔로워] 나를 팔로우 하고 있는 친구)
        List<FriendRelationship> friendRelationships = friendRelationshipRepository.findByReceiverEmail(email);
        List<String> friendEmails = friendRelationships.stream()
                .map(FriendRelationship::getSenderEmail)
                .toList();
        return friendEmails.stream()
                .map(GetFriendResponseDto::of)
                .collect(Collectors.toList());
    }
}
