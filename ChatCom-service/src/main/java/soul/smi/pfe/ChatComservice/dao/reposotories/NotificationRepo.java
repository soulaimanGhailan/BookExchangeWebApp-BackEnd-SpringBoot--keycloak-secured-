package soul.smi.pfe.ChatComservice.dao.reposotories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import soul.smi.pfe.ChatComservice.dao.entities.Notification;
@RepositoryRestResource
public interface NotificationRepo extends JpaRepository<Notification ,Long> {
}
