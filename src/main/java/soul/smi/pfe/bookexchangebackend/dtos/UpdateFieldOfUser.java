package soul.smi.pfe.bookexchangebackend.dtos;

import lombok.Data;
import soul.smi.pfe.bookexchangebackend.dao.enums.UpdateUserFieldType;

@Data
public class UpdateFieldOfUser {
    private String updateUserFieldType ;
    private String userId;
    private String data ;

}
