package soul.smi.pfe.statisticservice.model;

import lombok.Data;

@Data
public class UserEntity {
    private String userId;
    private String email ;
    private String firstname;
    private String username ;
    private String lastname;
    private String phoneNumber;
    private String imageContentBase64 ;
}
