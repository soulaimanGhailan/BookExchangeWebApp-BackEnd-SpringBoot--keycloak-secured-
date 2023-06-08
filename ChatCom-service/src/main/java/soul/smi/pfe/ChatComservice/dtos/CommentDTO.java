package soul.smi.pfe.ChatComservice.dtos;

import lombok.Data;
import soul.smi.pfe.ChatComservice.dao.enums.CommentType;
import soul.smi.pfe.ChatComservice.model.UserEntity;

import java.util.Date;

@Data
public class CommentDTO {
    private Long commentId;
    private String commentContent;
    private Date commentDate ;
    private CommentType commentType;
    private UserEntity owner;
    private PageInfo repliesPageInfo;
}
