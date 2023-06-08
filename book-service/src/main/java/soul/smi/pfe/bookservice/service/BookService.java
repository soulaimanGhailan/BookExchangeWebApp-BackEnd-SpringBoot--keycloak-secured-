package soul.smi.pfe.bookservice.service;


import soul.smi.pfe.bookservice.dtos.BookDTO;
import soul.smi.pfe.bookservice.dtos.PageInfo;
import soul.smi.pfe.bookservice.exeptions.BookNotFoundExeption;
import soul.smi.pfe.bookservice.exeptions.UserNotFoundExeption;
import soul.smi.pfe.bookservice.model.UserEntity;

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
    UserEntity getOwnerOfBook(Long bookId) throws BookNotFoundExeption, UserNotFoundExeption;
    PageInfo getPageInfo(int size);
    PageInfo getPageInfoOfSimilarBook(int size , String keyword);
    String[] getBooksCategories();
    List<BookDTO> getBooksByCategory(String Category , int page , int size);
    PageInfo getPageInfoOfSameCategory(String category, int size);
    PageInfo getPageInfoOfUserBooks(String userId, int size);
    BookDTO updateBook(BookDTO book) throws BookNotFoundExeption;
}
