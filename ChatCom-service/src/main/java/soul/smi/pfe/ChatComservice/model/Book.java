package soul.smi.pfe.ChatComservice.model;

import lombok.Data;

import java.util.Date;

@Data
public class Book {
    private Long bookId;
    private String bookTitle;
    private UserEntity owner ;
}
