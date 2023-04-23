package soul.smi.pfe.bookexchangebackend.dao.entities;
;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class RegisteredUser extends User{
    @Id
    private String userId;
    private String userPassword;
    private String email;
    @OneToOne
    private Picture profilePic;
    @OneToMany(mappedBy = "sender")
    private Collection<Message> sendMessages;
    @OneToMany(mappedBy = "reciever")
    private Collection<Message> recievedMessages;
    @OneToMany(mappedBy = "owner")
    private Collection<Book> books;
    @OneToMany(mappedBy = "owner")
    private Collection<Comment> comments;
    @OneToOne
    private UserAddress address;

}
