package soul.smi.pfe.ChatComservice.mappers;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import soul.smi.pfe.ChatComservice.dao.entities.Comment;
import soul.smi.pfe.ChatComservice.dtos.CommentDTO;
import soul.smi.pfe.ChatComservice.model.UserEntity;
import soul.smi.pfe.ChatComservice.service.UsersRestClient;


@Service
public class MapperImp implements Mapper {
    private UsersRestClient usersRestClient ;

    public MapperImp(UsersRestClient usersRestClient) {
        this.usersRestClient = usersRestClient;
    }

    @Override
    public CommentDTO fromComment(Comment comment) {
        CommentDTO commentDTO=new CommentDTO();
        UserEntity owner = usersRestClient.getUser(getToken(), comment.getOwnerId());
        comment.setOwner(owner);
        commentDTO.setOwner(owner);
        BeanUtils.copyProperties(comment,commentDTO);
        System.out.println(commentDTO);
        return commentDTO;
    }

    @Override
    public Comment fromCommentDTO(CommentDTO commentDTO) {
        Comment comment=new Comment();
        comment.setOwnerId(commentDTO.getOwner().getUserId());
        BeanUtils.copyProperties(commentDTO , comment);
        return comment;
    }

    private String getToken(){
        KeycloakSecurityContext context = (KeycloakSecurityContext) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        String token ="bearer "+ context.getTokenString();
        return token;
    }


}
