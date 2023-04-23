package soul.smi.pfe.bookexchangebackend.service;

import soul.smi.pfe.bookexchangebackend.dtos.BookDTO;
import soul.smi.pfe.bookexchangebackend.dtos.CommentDTO;
import soul.smi.pfe.bookexchangebackend.dtos.PageInfo;
import soul.smi.pfe.bookexchangebackend.dtos.RegisteredUserDTO;
import soul.smi.pfe.bookexchangebackend.exeptions.UserNotFoundExeption;
import soul.smi.pfe.bookexchangebackend.exeptions.bookNotFoundExeption;

import java.util.List;

public interface BookService {
    BookDTO findBook(Long bookId) throws bookNotFoundExeption;
    List<BookDTO> findSimilarBook(String isbn);
    List<BookDTO> getAllBooks();
    List<BookDTO> getAllBooksOfUser(String userId);
    List<BookDTO> getAllDisponibaleBooks();
    List<BookDTO> getBookByTitle(String title);
    void addBookToUser(String userId , BookDTO bookDTO , byte[] imageContent) throws  bookNotFoundExeption , UserNotFoundExeption;
    void deleteBook(Long bookId);
    void deleteAllBooksOfUser(String userId);
    byte[] getImageOfBook(Long bookId) throws bookNotFoundExeption;
    List<BookDTO> getAllBooksOfUserPage(String userId, int page, int size);
    List<BookDTO> getAllBooksPage(int page, int size);
    List<BookDTO> getBookByTitle(String title, int page, int size);
    List<BookDTO> getBookByKeyword(String keyword, int page, int size);
    RegisteredUserDTO getOwnerOfBook(Long bookId) throws bookNotFoundExeption;
    PageInfo getPageInfo( int size);
    PageInfo getPageInfoOfSimilarBook(int size , String keyword);
    String[] getBooksCategories();
    List<BookDTO> getBooksByCategory(String Category , int page , int size);
    PageInfo getPageInfoOfSameCategory(String category, int size);
    PageInfo getPageInfoOfUserBooks(String userId, int size);
}
