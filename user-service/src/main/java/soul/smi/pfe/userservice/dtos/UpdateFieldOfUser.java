package soul.smi.pfe.userservice.dtos;

import lombok.Data;

@Data
public class UpdateFieldOfUser {
    private String updateUserFieldType ;
    private String userId;
    private String data ;

}
