package soul.smi.pfe.bookexchangebackend.dao.reposotories;

import org.springframework.data.jpa.repository.JpaRepository;
import soul.smi.pfe.bookexchangebackend.dao.entities.UserEntity;

public interface UserEntityRepo extends JpaRepository<UserEntity , String> {

}
