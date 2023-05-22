package soul.smi.pfe.bookexchangebackend.dao.reposotories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import soul.smi.pfe.bookexchangebackend.dao.entities.Book;
import soul.smi.pfe.bookexchangebackend.dao.entities.Picture;
import soul.smi.pfe.bookexchangebackend.dao.enums.BookCategory;

import java.util.List;


public interface BookRepo extends JpaRepository<Book, Long> {
    List<Book> findByBookTitle(String title);
    List<Book>  findBookByIsbn(String isbn);
    List<Book> findBooksByOwnerUserId(String userId);
    Page<Book> findBooksByOwnerUserId(String userId , Pageable pageable);
    Page<Book> findByBookTitle(String title, Pageable pageable);
    Page<Book> findBooksByBookTitleContains(String keyword , Pageable pageable);
    Page<Book> findBooksByBookCategory(BookCategory category , Pageable pageable);
}
