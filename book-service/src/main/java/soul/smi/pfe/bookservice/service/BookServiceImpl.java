package soul.smi.pfe.bookservice.service;

import lombok.AllArgsConstructor;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soul.smi.pfe.bookservice.dao.entities.Book;
import soul.smi.pfe.bookservice.dao.enums.BookCategory;
import soul.smi.pfe.bookservice.dao.enums.BookStatus;
import soul.smi.pfe.bookservice.dao.reposotories.BookRepo;
import soul.smi.pfe.bookservice.dtos.BookDTO;
import soul.smi.pfe.bookservice.dtos.PageInfo;
import soul.smi.pfe.bookservice.exeptions.BookNotFoundExeption;
import soul.smi.pfe.bookservice.exeptions.UserNotFoundExeption;
import soul.smi.pfe.bookservice.mappers.Mapper;
import soul.smi.pfe.bookservice.model.Picture;
import soul.smi.pfe.bookservice.model.UserEntity;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private PictureRestClient pictureRestClient ;
    private CommentRestClient commentRestClient;
    private UsersRestClient usersRestClient ;
    private BookRepo bookRepo;
    private Mapper mapper;
    @Override
    public BookDTO findBook(Long bookId) throws BookNotFoundExeption {
        Book book = bookRepo.findById(bookId).orElseThrow(() -> new BookNotFoundExeption("book not found"));
        return mapper.fromBook(book);
    }
    @Override
    public List<BookDTO> findSimilarBook(String isbn) {
        return bookRepo.findBookByIsbn(isbn).stream()
                .map(book -> mapper.fromBook(book))
                .collect(Collectors.toList());
    }



    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepo.findAll().stream()
                .map(book -> mapper.fromBook(book))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> getAllBooksOfUser(String userId) {
        return bookRepo.findBooksByOwnerId(userId).stream()
                .map(book -> mapper.fromBook(book))
                .collect(Collectors.toList());
    }


    @Override
    public List<BookDTO> getAllDisponibaleBooks() {
        return bookRepo.findAll().stream()
                .filter(book -> BookStatus.AVAILABLE.equals(book.getBookStatus()))
                .map(book -> mapper.fromBook(book)).collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> getBookByTitle(String title) {
        return bookRepo.findByBookTitle(title).stream().map(book -> mapper.fromBook(book))
                .collect(Collectors.toList());
    }



    @Override
    public BookDTO addBookToUser(String userId, BookDTO bookDTO) throws UserNotFoundExeption {
        Book book = mapper.fromBookDTO(bookDTO);
        book.setAddingDate(new Date());
        UserEntity user = usersRestClient.getUser(getToken() , userId);
        if(user == null) throw  new UserNotFoundExeption("owner of book not found");
        book.setOwnerId(user.getUserId());
        Picture picture = pictureRestClient.createPic(getToken() , bookDTO.getImageContentBase64(), bookDTO.getBookTitle());
        book.setBookPictureId(picture.getId());
        Book saved = bookRepo.save(book);
        return mapper.fromBook(saved);
    }


    @Override
    public BookDTO deleteBook(Long bookId) throws BookNotFoundExeption {
        Book book = bookRepo.findById(bookId).orElseThrow(() -> new BookNotFoundExeption("book not found"));
        BookDTO bookDTO = mapper.fromBook(book);
        commentRestClient.deleteAllCommentOfBook(getToken() , bookId);
        pictureRestClient.deletePictureOfBook(getToken() ,book.getBookPictureId());
        bookRepo.deleteById(bookId);
        return bookDTO;
    }

    @Override
    public void deleteAllBooksOfUser(String userId) {
        getAllBooksOfUser(userId).stream().forEach(bookDTO -> {
            try {
                deleteBook(bookDTO.getBookId());
            } catch (BookNotFoundExeption e) {
                throw new RuntimeException(e);
            }
        });
    }



    @Override
    public List<BookDTO> getAllBooksOfUserPage(String userId, int page, int size) {
        Page<Book> bookPage = bookRepo.findBooksByOwnerId(userId, PageRequest.of(page, size));
        return bookPage.getContent().stream().map(book -> mapper.fromBook(book))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> getAllBooksPage(int page, int size) {
        Page<Book> pageBook = bookRepo.findAll(PageRequest.of(page, size));
        return pageBook.getContent().stream().map(book -> mapper.fromBook(book))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> getBookByTitle(String title, int page, int size) {
        Page<Book> bookPage = bookRepo.findByBookTitle(title, PageRequest.of(page, size));
        return bookPage.getContent().stream().map(book -> mapper.fromBook(book)).collect(Collectors.toList());

    }

    @Override
    public List<BookDTO> getBookByKeyword(String keyword, int page, int size) {
        Page<Book> bookPage = bookRepo.findBooksByBookTitleContains(keyword, PageRequest.of(page, size));
        return bookPage.getContent().stream()
                .map(book -> mapper.fromBook(book)).collect(Collectors.toList());
    }

    @Override
    public UserEntity getOwnerOfBook(Long bookId) throws BookNotFoundExeption, UserNotFoundExeption {
        Book book = bookRepo.findById(bookId).orElseThrow(() -> new BookNotFoundExeption("book not found"));
        UserEntity owner = usersRestClient.getUser(getToken() , book.getOwnerId());
        if(owner == null) throw  new UserNotFoundExeption("owner of book not found");
        return owner;
    }


    @Override
    public PageInfo getPageInfo(int size) {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setTotalPages(bookRepo.findAll(PageRequest.of(0, size)).getTotalPages());
        pageInfo.setTotalElements(bookRepo.findAll(PageRequest.of(0, size)).getTotalElements());
        return pageInfo;
    }

    @Override
    public PageInfo getPageInfoOfSimilarBook(int size, String keyword) {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setTotalPages(bookRepo.findBooksByBookTitleContains(keyword, PageRequest.of(0, size)).getTotalPages());
        pageInfo.setTotalElements(bookRepo.findBooksByBookTitleContains(keyword, PageRequest.of(0, size)).getTotalElements());
        return pageInfo;
    }

    @Override
    public String[] getBooksCategories() {
        return new String[0];
    }

    @Override
    public List<BookDTO> getBooksByCategory(String category, int page, int size) {
        Page<Book> books = this.bookRepo.findBooksByBookCategory(BookCategory.valueOf(category), PageRequest.of(page, size));
        return books.getContent().stream().map(book -> mapper.fromBook(book)).collect(Collectors.toList());
    }

    @Override
    public PageInfo getPageInfoOfSameCategory(String category, int size) {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setTotalElements(this.bookRepo.findBooksByBookCategory(BookCategory.valueOf(category), PageRequest.of(0, size)).getTotalElements());
        pageInfo.setTotalPages(this.bookRepo.findBooksByBookCategory(BookCategory.valueOf(category), PageRequest.of(0, size)).getTotalPages());
        return pageInfo;
    }

    @Override
    public PageInfo getPageInfoOfUserBooks(String userId, int size) {
        PageInfo pageInfo=new PageInfo();
        Page<Book> page = bookRepo.findBooksByOwnerId(userId, PageRequest.of(0, size));
        pageInfo.setTotalPages(page.getTotalPages());
        pageInfo.setTotalElements(page.getTotalElements());
        return pageInfo;
    }

    @Override
    public BookDTO updateBook(BookDTO bookDTO) throws BookNotFoundExeption {
        Book book = bookRepo.findById(bookDTO.getBookId()).orElseThrow(() -> new BookNotFoundExeption("book not found"));
        book.setBookTitle(bookDTO.getBookTitle());
        book.setBookCategory(bookDTO.getBookCategory());
        book.setDescription(bookDTO.getDescription());
        book.setBookStatus(bookDTO.getBookStatus());
        Picture picture = pictureRestClient.updateBookPic(getToken(), bookDTO.getImageContentBase64(), book.getBookPictureId());
        book.setBookPictureId(picture.getId());
        Book saved = bookRepo.save(book);
        return mapper.fromBook(saved);
    }

    private String getToken(){
        KeycloakSecurityContext context = (KeycloakSecurityContext) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        String token ="bearer "+ context.getTokenString();
        return token;
    }
}
