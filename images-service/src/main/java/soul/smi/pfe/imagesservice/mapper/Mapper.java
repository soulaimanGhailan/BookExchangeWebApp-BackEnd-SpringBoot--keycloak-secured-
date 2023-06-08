package soul.smi.pfe.imagesservice.mapper;

import soul.smi.pfe.imagesservice.dao.entities.Picture;
import soul.smi.pfe.imagesservice.dto.PictureDto;

public interface Mapper {
    PictureDto fromPicture(Picture picture);
    Picture fromPicture(PictureDto pictureDto);
}
