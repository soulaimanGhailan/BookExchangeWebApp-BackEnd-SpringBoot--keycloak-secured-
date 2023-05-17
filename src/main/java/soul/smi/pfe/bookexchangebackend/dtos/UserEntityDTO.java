package soul.smi.pfe.bookexchangebackend.dtos;

import lombok.Data;

@Data
public class UserEntityDTO {
    private String userId;
    private String email ;
    private String firstname;
    private String username ;
    private String lastname;
    private String phoneNumber;
}
