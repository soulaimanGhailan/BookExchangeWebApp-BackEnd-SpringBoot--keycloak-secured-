package soul.smi.pfe.userservice.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data @Builder
public class Picture {
    private Long id;
    private Date addingDate;
    private String pictureName;
    private String pictureContent;
}
