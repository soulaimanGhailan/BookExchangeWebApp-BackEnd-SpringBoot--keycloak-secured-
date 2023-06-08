package soul.smi.pfe.bookservice.dao.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import soul.smi.pfe.bookservice.dao.enums.BookCategory;
import soul.smi.pfe.bookservice.dao.enums.BookStatus;
import soul.smi.pfe.bookservice.dao.enums.BookType;
import soul.smi.pfe.bookservice.model.Comment;
import soul.smi.pfe.bookservice.model.Picture;
import soul.smi.pfe.bookservice.model.UserEntity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;
    private String isbn;
    private String bookTitle;
    private String author;
    @Enumerated(EnumType.STRING)
    private BookType bookType;
    @Enumerated(EnumType.STRING)
    private BookCategory bookCategory;
    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;
    private String description;
    private Integer editionNumber;

    private Long bookPictureId;
    private String ownerId ;
    private Date addingDate;
    @Transient
    private UserEntity owner ;
    @Transient
    private Collection<Comment> comments;
    @Transient
    private Picture bookPicture;
}
