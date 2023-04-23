package soul.smi.pfe.bookexchangebackend.dao.reposotories;


import org.springframework.data.jpa.repository.JpaRepository;
import soul.smi.pfe.bookexchangebackend.dao.entities.Message;

public interface MessageRepo extends JpaRepository<Message, Long> {
}
