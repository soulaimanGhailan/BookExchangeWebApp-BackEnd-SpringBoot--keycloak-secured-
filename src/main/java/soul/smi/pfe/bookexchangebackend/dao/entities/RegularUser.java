package soul.smi.pfe.bookexchangebackend.dao.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegularUser extends  RegisteredUser{
    private String job;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private Date birthday;
}
