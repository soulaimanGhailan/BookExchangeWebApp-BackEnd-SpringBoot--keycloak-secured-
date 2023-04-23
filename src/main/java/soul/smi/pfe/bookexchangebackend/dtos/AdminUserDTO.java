package soul.smi.pfe.bookexchangebackend.dtos;

import lombok.Data;

@Data
public class AdminUserDTO extends RegularUserDTO{
    private String adminDescription;
}
