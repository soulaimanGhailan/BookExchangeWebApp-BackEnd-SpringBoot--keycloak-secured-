package soul.smi.pfe.bookexchangebackend.dtos;

import lombok.Data;


@Data
public class RegularUserDTO extends RegisteredUserDTO{
    private String firstname;
    private String lastname;
    private String phoneNumber;
}
