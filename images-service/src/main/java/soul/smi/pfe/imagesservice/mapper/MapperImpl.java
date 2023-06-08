package soul.smi.pfe.imagesservice.mapper;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import soul.smi.pfe.imagesservice.dao.entities.Picture;
import soul.smi.pfe.imagesservice.dto.PictureDto;
@Service
public class MapperImpl implements Mapper {
    @Override
    public PictureDto fromPicture(Picture picture) {
        PictureDto pictureDto=new PictureDto();
        String base64Image = Base64.encodeBase64String(picture.getPictureContent());
        pictureDto.setPictureContent(base64Image);
        BeanUtils.copyProperties(picture , pictureDto);
        return pictureDto;
    }

    @Override
    public Picture fromPicture(PictureDto pictureDto) {
        Picture picture=new Picture();
        String imageBase64=pictureDto.getPictureContent();
        if(imageBase64.startsWith("data:image/jpeg;base64,")) {
            imageBase64 = imageBase64.substring(imageBase64.indexOf(",") + 1);
        }
        byte[] picContent = Base64.decodeBase64(imageBase64);
        picture.setPictureContent(picContent);
        BeanUtils.copyProperties(pictureDto , picture);
        return picture;
    }
}
