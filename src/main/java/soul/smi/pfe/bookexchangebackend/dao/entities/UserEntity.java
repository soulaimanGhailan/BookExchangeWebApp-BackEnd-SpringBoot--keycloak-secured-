package soul.smi.pfe.bookexchangebackend.dao.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
