package soul.smi.pfe.bookexchangebackend.dao.reposotories;

import org.springframework.data.jpa.repository.JpaRepository;
import soul.smi.pfe.bookexchangebackend.dao.entities.UserAddress;

public interface UserAddressRepo extends JpaRepository<UserAddress, Long> {

}
