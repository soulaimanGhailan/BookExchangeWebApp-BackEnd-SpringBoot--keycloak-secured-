package soul.smi.pfe.bookservice.dtos;

import lombok.Data;
import soul.smi.pfe.bookservice.dao.enums.BookCategory;
import soul.smi.pfe.bookservice.dao.enums.BookType;

@Data
public class BookOfUserHelper {
    private Long bookId;
    private String ISBN;
    private String bookTitle;
    private String author;
    private String photo;
    private BookType bookType;
    private BookCategory bookCategory;
    private String description;
    private int editionNumber;
}
