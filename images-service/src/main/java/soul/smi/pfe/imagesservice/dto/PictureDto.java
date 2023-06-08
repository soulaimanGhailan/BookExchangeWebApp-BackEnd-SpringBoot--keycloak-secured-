package soul.smi.pfe.imagesservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor @NoArgsConstructor
public class PictureDto {
    private Long id;
    private Date addingDate;
    private String pictureName;
    private String pictureContent;
}
