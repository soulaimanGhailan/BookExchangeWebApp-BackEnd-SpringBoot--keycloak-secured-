package soul.smi.pfe.userservice.dao.reposotories;


import org.springframework.data.jpa.repository.JpaRepository;
import soul.smi.pfe.userservice.dao.entities.UserAddress;

public interface UserAddressRepo extends JpaRepository<UserAddress, Long> {

}
