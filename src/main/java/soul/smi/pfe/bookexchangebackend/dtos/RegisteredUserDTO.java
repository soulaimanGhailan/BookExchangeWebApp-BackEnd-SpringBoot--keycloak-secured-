package soul.smi.pfe.bookexchangebackend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
@Data
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "userId")
public class RegisteredUserDTO {
    private String userId;
    private String email ;
}
