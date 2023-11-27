package gdsc.cokepoke.repository;

import gdsc.cokepoke.entity.FriendRelationship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRelationshipRepository extends JpaRepository<FriendRelationship, Long> {
    List<FriendRelationship> findBySenderEmail(String senderEmail);
    List<FriendRelationship> findByReceiverEmail(String receiverEmail);
    boolean existsBySenderEmailAndReceiverEmail(String senderEmail, String receiverEmail);
}
