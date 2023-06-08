package soul.smi.pfe.userservice.dao.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import soul.smi.pfe.userservice.model.Book;
import soul.smi.pfe.userservice.model.Comment;
import soul.smi.pfe.userservice.model.Picture;
import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    private String userId;
    private String email;
    private String firstname;
    private String username;
    private String lastname;
    private String phoneNumber;
    private String job;
    private Date birthday;
    private Long pictureId;
    @OneToOne
    private UserAddress address;
    @Transient
    private Picture profilePic;
    @Transient
    private Collection<Book> books;
    @Transient
    private Collection<Comment> comments;

}
