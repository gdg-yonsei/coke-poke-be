package gdsc.cokepoke.repository;

import gdsc.cokepoke.domain.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String Username);
    boolean existsByUsername(String Username);
    User findByName(String name);

}
