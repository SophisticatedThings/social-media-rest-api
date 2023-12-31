package artem.strelcov.subscriptionsservice.repository;

import artem.strelcov.subscriptionsservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public User findUserByUsername(String username);

}
