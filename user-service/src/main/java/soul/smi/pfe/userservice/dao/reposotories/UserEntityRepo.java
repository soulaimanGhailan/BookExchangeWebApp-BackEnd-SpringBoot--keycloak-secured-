package soul.smi.pfe.userservice.dao.reposotories;


import org.springframework.data.jpa.repository.JpaRepository;
import soul.smi.pfe.userservice.dao.entities.UserEntity;

public interface UserEntityRepo extends JpaRepository<UserEntity, String> {

}
