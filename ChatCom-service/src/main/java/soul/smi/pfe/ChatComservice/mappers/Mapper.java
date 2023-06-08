package soul.smi.pfe.ChatComservice.mappers;


import soul.smi.pfe.ChatComservice.dao.entities.Comment;
import soul.smi.pfe.ChatComservice.dtos.CommentDTO;

public interface Mapper {
    CommentDTO fromComment(Comment comment);
    Comment fromCommentDTO(CommentDTO commentDTO);

}
