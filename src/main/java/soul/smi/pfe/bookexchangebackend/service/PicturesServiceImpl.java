package soul.smi.pfe.bookexchangebackend.service;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soul.smi.pfe.bookexchangebackend.dao.entities.Picture;
import soul.smi.pfe.bookexchangebackend.dao.reposotories.PictureRepo;

import java.util.Date;
@Service
@Transactional
public class PicturesServiceImpl implements PicturesService {
    private PictureRepo pictureRepo;

    public PicturesServiceImpl(PictureRepo pictureRepo) {
        this.pictureRepo = pictureRepo;
    }

    @Override
    public Picture createPicFromBase64(String name , String imageBase64)
    {
        Picture picture=new Picture();
        picture.setAddingDate(new Date());
        String base64Data = imageBase64;
        if(imageBase64.startsWith("data:image/jpeg;base64,")) {
            base64Data = imageBase64.substring(imageBase64.indexOf(",") + 1);
        }
        byte[] picContent = Base64.decodeBase64(base64Data);
        picture.setPictureContent(picContent);
        picture.setPictureName(name);
        Picture savedPic = pictureRepo.save(picture);
        return savedPic;

    }

    @Override
    public Picture upDatePicture(Long picId, String name, String imageBase64) {
        Picture picture = pictureRepo.findById(picId).orElseThrow(() -> new RuntimeException("image not found"));
        String base64Data = imageBase64;
        if(imageBase64.startsWith("data:image/jpeg;base64,")) {
            base64Data = imageBase64.substring(imageBase64.indexOf(",") + 1);
        }
        byte[] picContent = Base64.decodeBase64(base64Data);
        picture.setPictureContent(picContent);
        picture.setPictureName(name);
        Picture savedPic = pictureRepo.save(picture);
        return savedPic;
    }
}
