package soul.smi.pfe.bookexchangebackend.dao.reposotories;

import org.springframework.data.jpa.repository.JpaRepository;
import soul.smi.pfe.bookexchangebackend.dao.entities.RegisteredUser;

public interface RegisteredUserRepo extends JpaRepository<RegisteredUser, String> {
}
