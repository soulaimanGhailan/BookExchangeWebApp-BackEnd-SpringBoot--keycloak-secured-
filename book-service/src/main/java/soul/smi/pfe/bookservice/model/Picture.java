package soul.smi.pfe.bookservice.model;

import lombok.Data;

import java.util.Date;
@Data
public class Picture {
    private Long id;
    private Date addingDate;
    private String pictureName;
    private String pictureContent;
}
