package soul.smi.pfe.bookexchangebackend.dao.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
public class AdminUser extends  RegisteredUser{
    private Long adminNum;
    private String adminDescription;
}
