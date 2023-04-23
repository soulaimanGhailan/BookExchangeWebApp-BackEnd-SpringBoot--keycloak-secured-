package soul.smi.pfe.bookexchangebackend.dao.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import soul.smi.pfe.bookexchangebackend.dao.enums.CommentType;

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
    @ManyToOne
    private RegisteredUser owner;
    @ManyToOne
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Book commentBook;
    @OneToMany(mappedBy = "originalComment")
    private Collection<Comment> repliedComments;
    @ManyToOne
    private Comment originalComment;
    @Enumerated(EnumType.STRING)
    private CommentType commentType;
}
