package soul.smi.pfe.imagesservice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InitImagesImpl implements InitImages {
    private PicturesService picturesService ;

    public InitImagesImpl(PicturesService picturesService) {
        this.picturesService = picturesService;
    }

    @Override
    public void initImages() {
        picturesService.createDefaultPic("defaultProfilePicture");
        for (int i = 0; i < 8; i++) {
            picturesService.createBookImage("book "+ i ,1 );
        }
        for (int i = 0; i < 8; i++) {
            picturesService.createBookImage("book "+ i ,2 );
        }
        for (int i = 0; i < 8; i++) {
            picturesService.createBookImage("book "+ i ,3 );
        }

    }
}
