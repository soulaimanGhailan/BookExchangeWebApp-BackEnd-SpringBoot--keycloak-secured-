package soul.smi.pfe.ChatComservice.dao.reposotories;

import org.springframework.data.jpa.repository.JpaRepository;
import soul.smi.pfe.ChatComservice.dao.entities.Message;

public interface MessageRepo extends JpaRepository<Message , Long> {
}
