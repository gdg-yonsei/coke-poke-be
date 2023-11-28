package gdsc.cokepoke.repository;

import gdsc.cokepoke.entity.Coke;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CokeRepository extends JpaRepository<Coke, Long> {
    @Query("SELECT COUNT(c) > 0 FROM Coke c WHERE "+
            "c.senderEmail = :senderEmail AND "+
            "c.receiverEmail = :receiverEmail AND "+
            "CAST(c.createdDate AS DATE) = :today")
    boolean existsBySenderAndReceiverAndCreatedDate(
            @Param("senderEmail") String senderEmail,
            @Param("receiverEmail") String receiverEmail,
            @Param("today") LocalDate today);

    @Query("SELECT c FROM Coke c WHERE "+
            "c.chatroom.id = :chatroomId")
    List<Coke> findByChatroomId(@Param("chatroomId") Long chatroomId);
}
