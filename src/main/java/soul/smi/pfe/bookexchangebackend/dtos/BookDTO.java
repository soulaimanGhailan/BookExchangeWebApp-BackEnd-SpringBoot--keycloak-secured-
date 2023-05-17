package soul.smi.pfe.bookexchangebackend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import soul.smi.pfe.bookexchangebackend.dao.enums.BookCategory;
import soul.smi.pfe.bookexchangebackend.dao.enums.BookStatus;
import soul.smi.pfe.bookexchangebackend.dao.enums.BookType;
import java.util.Collection;
import java.util.Date;

@Data
public class BookDTO {
    private Long bookId;
    private String ISBN;
    private String bookTitle;
    private String author;
    private BookStatus bookStatus;
    private BookType bookType;
    private BookCategory bookCategory;
    private String description;
    private Date addingDate;
    private Integer editionNumber;
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UserEntityDTO owner ;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<CommentDTO> comments;
}
