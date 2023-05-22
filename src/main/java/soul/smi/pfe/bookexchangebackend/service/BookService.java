package soul.smi.pfe.bookexchangebackend.service;

import soul.smi.pfe.bookexchangebackend.dtos.*;
import soul.smi.pfe.bookexchangebackend.exeptions.UserNotFoundExeption;
import soul.smi.pfe.bookexchangebackend.exeptions.BookNotFoundExeption;

import java.util.List;

public interface BookService {
    BookDTO findBook(Long bookId) throws BookNotFoundExeption;
    List<BookDTO> findSimilarBook(String isbn);
    List<BookDTO> getAllBooks();
    List<BookDTO> getAllBooksOfUser(String userId);
    List<BookDTO> getAllDisponibaleBooks();
    List<BookDTO> getBookByTitle(String title);
    BookDTO addBookToUser(String userId , BookDTO bookDTO) throws BookNotFoundExeption, UserNotFoundExeption;
    BookDTO deleteBook(Long bookId) throws BookNotFoundExeption;
    void deleteAllBooksOfUser(String userId);
    List<BookDTO> getAllBooksOfUserPage(String userId, int page, int size);
    List<BookDTO> getAllBooksPage(int page, int size);
    List<BookDTO> getBookByTitle(String title, int page, int size);
    List<BookDTO> getBookByKeyword(String keyword, int page, int size);
    UserEntityDTO getOwnerOfBook(Long bookId) throws BookNotFoundExeption;
    PageInfo getPageInfo( int size);
    PageInfo getPageInfoOfSimilarBook(int size , String keyword);
    String[] getBooksCategories();
    List<BookDTO> getBooksByCategory(String Category , int page , int size);
    PageInfo getPageInfoOfSameCategory(String category, int size);
    PageInfo getPageInfoOfUserBooks(String userId, int size);
    BookDTO updateBook(BookDTO book) throws BookNotFoundExeption;
}
