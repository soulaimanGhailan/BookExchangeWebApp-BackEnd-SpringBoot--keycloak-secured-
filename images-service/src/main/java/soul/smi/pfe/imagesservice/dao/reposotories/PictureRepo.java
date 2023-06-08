package soul.smi.pfe.imagesservice.dao.reposotories;

import org.springframework.data.jpa.repository.JpaRepository;
import soul.smi.pfe.imagesservice.dao.entities.Picture;

public interface PictureRepo extends JpaRepository<Picture, Long> {

}
