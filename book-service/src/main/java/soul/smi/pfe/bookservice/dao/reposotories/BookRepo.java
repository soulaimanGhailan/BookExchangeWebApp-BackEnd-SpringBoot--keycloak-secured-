package soul.smi.pfe.bookservice.dao.reposotories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import soul.smi.pfe.bookservice.dao.entities.Book;
import soul.smi.pfe.bookservice.dao.enums.BookCategory;

import java.util.List;


public interface BookRepo extends JpaRepository<Book, Long> {
    List<Book> findByBookTitle(String title);
    List<Book>  findBookByIsbn(String isbn);
    List<Book> findBooksByOwnerId(String userId);
    Page<Book> findBooksByOwnerId(String userId , Pageable pageable);
    Page<Book> findByBookTitle(String title, Pageable pageable);
    Page<Book> findBooksByBookTitleContains(String keyword , Pageable pageable);
    Page<Book> findBooksByBookCategory(BookCategory category , Pageable pageable);
}
