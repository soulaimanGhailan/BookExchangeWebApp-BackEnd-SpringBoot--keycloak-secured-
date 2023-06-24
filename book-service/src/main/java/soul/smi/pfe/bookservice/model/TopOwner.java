package soul.smi.pfe.bookservice.model;

import lombok.Data;

@Data
public class TopOwner {
    private UserEntity owner ;
    private Long booksNumber ;
}
