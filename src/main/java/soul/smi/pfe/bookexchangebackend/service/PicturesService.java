package soul.smi.pfe.bookexchangebackend.service;

import soul.smi.pfe.bookexchangebackend.dao.entities.Picture;

public interface PicturesService {
    Picture createPicFromBase64(String name , String imageBase64);
    Picture upDatePicture(Long picId , String name , String imageBase64);
}
