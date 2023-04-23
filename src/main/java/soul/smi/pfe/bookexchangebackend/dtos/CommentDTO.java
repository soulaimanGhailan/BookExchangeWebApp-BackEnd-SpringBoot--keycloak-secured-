package soul.smi.pfe.bookexchangebackend.dtos;

import lombok.Data;
import soul.smi.pfe.bookexchangebackend.dao.enums.CommentType;

import java.util.Date;

@Data
public class CommentDTO {
    private Long commentId;
    private String commentContent;
    private Date commentDate ;
    private CommentType commentType;
    private RegisteredUserDTO owner;
    private PageInfo repliesPageInfo;
}
