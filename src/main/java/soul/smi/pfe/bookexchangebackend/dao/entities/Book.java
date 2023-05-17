package soul.smi.pfe.bookexchangebackend.dao.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import soul.smi.pfe.bookexchangebackend.dao.enums.BookCategory;
import soul.smi.pfe.bookexchangebackend.dao.enums.BookStatus;
import soul.smi.pfe.bookexchangebackend.dao.enums.BookType;

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
    private Date addingDate;
    @ManyToOne
    private UserEntity owner ;
    @OneToMany(mappedBy = "commentBook")
    private Collection<Comment> comments;
    @OneToOne
    private Picture bookPicture;
}
