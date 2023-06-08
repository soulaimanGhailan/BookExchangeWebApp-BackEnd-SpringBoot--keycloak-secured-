package soul.smi.pfe.ChatComservice.dao.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import soul.smi.pfe.ChatComservice.dao.enums.CommentType;
import soul.smi.pfe.ChatComservice.model.Book;
import soul.smi.pfe.ChatComservice.model.UserEntity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    private String commentContent;
    private Date commentDate ;

    private String ownerId ;
    private Long bookId ;
    @Transient
    private UserEntity owner;
    @Transient
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Book commentBook;
    @OneToMany(mappedBy = "originalComment")
    private Collection<Comment> repliedComments;
    @ManyToOne
    private Comment originalComment;
    @Enumerated(EnumType.STRING)
    private CommentType commentType;
}
