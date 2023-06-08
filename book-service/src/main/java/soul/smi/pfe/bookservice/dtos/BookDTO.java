package soul.smi.pfe.bookservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import soul.smi.pfe.bookservice.dao.enums.BookCategory;
import soul.smi.pfe.bookservice.dao.enums.BookStatus;
import soul.smi.pfe.bookservice.dao.enums.BookType;
import soul.smi.pfe.bookservice.model.UserEntity;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private Long bookId;
    private String ISBN;
    private String bookTitle;
    private String author;
    private String imageContentBase64;
    private BookStatus bookStatus;
    private BookType bookType;
    private BookCategory bookCategory;
    private String description;
    private Date addingDate;
    private Integer editionNumber;
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UserEntity owner ;
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    private Collection<CommentDTO> comments;
}
