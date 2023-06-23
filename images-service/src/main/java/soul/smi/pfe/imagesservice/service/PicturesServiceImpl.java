package soul.smi.pfe.imagesservice.service;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soul.smi.pfe.imagesservice.dao.entities.Picture;
import soul.smi.pfe.imagesservice.dao.reposotories.PictureRepo;
import soul.smi.pfe.imagesservice.dto.PictureDto;
import soul.smi.pfe.imagesservice.mapper.Mapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;


@Service
@Transactional
public class PicturesServiceImpl implements PicturesService {
    private PictureRepo pictureRepo;
    private Mapper mapper;
    public PicturesServiceImpl(PictureRepo pictureRepo, Mapper mapper) {
        this.pictureRepo = pictureRepo;
        this.mapper = mapper;
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
    public Picture upDatePicture(Long picId , String imageBase64) {
        Picture picture = pictureRepo.findById(picId).orElseThrow(() -> new RuntimeException("image not found"));
        String base64Data = imageBase64;
        if(imageBase64.startsWith("data:image/jpeg;base64,")) {
            base64Data = imageBase64.substring(imageBase64.indexOf(",") + 1);
        }
        byte[] picContent = Base64.decodeBase64(base64Data);
        picture.setPictureContent(picContent);
        Picture savedPic = pictureRepo.save(picture);
        return savedPic;
    }

    @Override
    public Picture createDefaultPic(String picName) {
        File file=new File("C:\\formation\\java path\\jee\\PFe\\Nouveau dossier (2)\\microserives Imegration\\images-service\\initImages\\book\\profile\\defaultProfile.jpg");
        Path path= Paths.get(file.toURI());
        try {
            byte[] picContent = Files.readAllBytes(path);
            Picture picture1 = new Picture();
            picture1.setPictureContent(picContent);
            picture1.setAddingDate(new Date());
            picture1.setPictureName(picName);
            return pictureRepo.save(picture1);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public PictureDto getPicture(Long id) {

        Picture pic = pictureRepo.findById(id).orElseThrow(() -> new RuntimeException("picture getting error"));
        return mapper.fromPicture(pic);
    }

    @Override
    public Picture deletePic(Long id) {
        Picture picture = pictureRepo.findById(id).get();
        pictureRepo.deleteById(id);
        return picture;
    }
    @Override
    public Picture createBookImage(String name, int i) {
        try {
            return createImage("C:\\formation\\java path\\jee\\PFe\\Nouveau dossier (2)\\microserives Imegration\\images-service\\initImages\\book\\bookImages" , name , i);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private Picture createImage(String pathName , String name , int numberImages)throws IOException{
        File file=new File(pathName+"\\"+numberImages+".jpg");
        Path path= Paths.get(file.toURI());
        byte[] picContent = Files.readAllBytes(path);
        Picture picture1 = new Picture();
        picture1.setPictureContent(picContent);
        picture1.setAddingDate(new Date());
        picture1.setPictureName(name);
        return pictureRepo.save(picture1);
    }


}
