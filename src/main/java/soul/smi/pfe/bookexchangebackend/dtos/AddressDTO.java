package soul.smi.pfe.bookexchangebackend.dtos;

import lombok.Data;

@Data
public class AddressDTO {
    private String country;
    private String city;
    private String homeAddress;
    private String postCode;
}
