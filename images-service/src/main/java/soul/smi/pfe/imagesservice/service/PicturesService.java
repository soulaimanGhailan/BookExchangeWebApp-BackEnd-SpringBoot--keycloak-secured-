package soul.smi.pfe.imagesservice.service;


import soul.smi.pfe.imagesservice.dao.entities.Picture;
import soul.smi.pfe.imagesservice.dto.PictureDto;

import java.io.IOException;

public interface PicturesService {
    Picture createPicFromBase64(String name , String imageBase64);
    Picture upDatePicture(Long picId  , String imageBase64);
    Picture createDefaultPic(String picName);
    PictureDto getPicture(Long id);
    Picture deletePic(Long id);
    Picture createBookImage(String name);
}
