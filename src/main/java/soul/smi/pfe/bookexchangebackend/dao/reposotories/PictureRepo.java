package soul.smi.pfe.bookexchangebackend.dao.reposotories;

import org.springframework.data.jpa.repository.JpaRepository;
import soul.smi.pfe.bookexchangebackend.dao.entities.Picture;

public interface PictureRepo extends JpaRepository<Picture , Long> {
}
