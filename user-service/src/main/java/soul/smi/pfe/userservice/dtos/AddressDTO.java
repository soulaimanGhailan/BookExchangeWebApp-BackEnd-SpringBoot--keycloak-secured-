package soul.smi.pfe.userservice.dtos;

import lombok.Data;

@Data
public class AddressDTO {
    private String country;
    private String city;
    private String homeAddress;
    private String postCode;
}
