package gdsc.cokepoke.repository;

import gdsc.cokepoke.entity.Chatroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatroomRepository extends JpaRepository<Chatroom, Long> {
    @Query("select c from Chatroom c where "+
            "(c.member1Email = :member1Email and c.member2Email = :member2Email) or "+
            "(c.member1Email = :member2Email and c.member2Email = :member1Email)")
    Optional<Chatroom> findByFirstEmailAndSecondEmail(
            @Param("member1Email") String firstEmail,
            @Param("member2Email") String secondEmail);

    @Query("select c from Chatroom c where "+
            "c.member1Email = :memberEmail or "+
            "c.member2Email = :memberEmail")
    List<Chatroom> findByMemberEmail(
            @Param("memberEmail") String memberEmail);
}
