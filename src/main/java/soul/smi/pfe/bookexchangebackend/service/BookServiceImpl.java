package soul.smi.pfe.bookexchangebackend.service;

import lombok.AllArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soul.smi.pfe.bookexchangebackend.dao.entities.*;
import soul.smi.pfe.bookexchangebackend.dao.enums.BookCategory;
import soul.smi.pfe.bookexchangebackend.dao.enums.BookStatus;
import soul.smi.pfe.bookexchangebackend.dao.reposotories.BookRepo;
import soul.smi.pfe.bookexchangebackend.dao.reposotories.PictureRepo;
import soul.smi.pfe.bookexchangebackend.dao.reposotories.UserEntityRepo;
import soul.smi.pfe.bookexchangebackend.dtos.BookDTO;
import soul.smi.pfe.bookexchangebackend.dtos.PageInfo;
import soul.smi.pfe.bookexchangebackend.dtos.UserEntityDTO;
import soul.smi.pfe.bookexchangebackend.exeptions.UserNotFoundExeption;
import soul.smi.pfe.bookexchangebackend.exeptions.BookNotFoundExeption;
import soul.smi.pfe.bookexchangebackend.mappers.Mapper;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private CommentService commentService;
    private UserEntityRepo userRepo;
    private BookRepo bookRepo;
    private Mapper mapper;
    private PictureRepo pictureRepo;
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
        return bookRepo.findBooksByOwnerUserId(userId).stream()
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
    public BookDTO addBookToUser(String userId, BookDTO bookDTO) throws  UserNotFoundExeption {
        Book book = mapper.fromBookDTO(bookDTO);
        book.setAddingDate(new Date());
        UserEntity user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundExeption("user not found"));
        book.setOwner(user);
        Book saved = bookRepo.save(book);
        return mapper.fromBook(saved);
    }


    @Override
    public BookDTO deleteBook(Long bookId) throws BookNotFoundExeption {
        // we have to remove comment of this book before deleting it cause the comments has a forign key to book
        Book book = bookRepo.findById(bookId).orElseThrow(() -> new BookNotFoundExeption("book not found"));
        commentService.deleteAllCommentOfBook(bookId);
        bookRepo.deleteById(bookId);
        /** in regular case this is going to work
         * but in test case (there is only 7images shared between books) it is not going to work  **/
        // we will fix this by creating an image for evry book
        pictureRepo.deleteById(book.getBookPicture().getId());
        return mapper.fromBook(book);
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
        Page<Book> bookPage = bookRepo.findBooksByOwnerUserId(userId, PageRequest.of(page, size));
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
    public UserEntityDTO getOwnerOfBook(Long bookId) throws BookNotFoundExeption {
        UserEntity owner  = bookRepo.findById(bookId).orElseThrow(() -> new BookNotFoundExeption("book not found")).getOwner();
       return mapper.fromUserEntity(owner);
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
        Page<Book> page = bookRepo.findBooksByOwnerUserId(userId, PageRequest.of(0, size));
        pageInfo.setTotalPages(page.getTotalPages());
        pageInfo.setTotalElements(page.getTotalElements());
        return pageInfo;
    }

    @Override
    public BookDTO updateBook(BookDTO book) throws BookNotFoundExeption {
        Book book1 = bookRepo.findById(book.getBookId()).orElseThrow(() -> new BookNotFoundExeption("book not found"));
        Long oldPicId = book1.getBookPicture().getId();
        book1=mapper.fromBookDTO(book);
        Book saved = bookRepo.save(book1);
        pictureRepo.deleteById(oldPicId);
        return mapper.fromBook(saved);
    }




}
