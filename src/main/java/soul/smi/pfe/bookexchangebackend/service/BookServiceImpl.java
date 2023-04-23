package soul.smi.pfe.bookexchangebackend.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import soul.smi.pfe.bookexchangebackend.dao.entities.*;
import soul.smi.pfe.bookexchangebackend.dao.enums.BookCategory;
import soul.smi.pfe.bookexchangebackend.dao.enums.BookStatus;
import soul.smi.pfe.bookexchangebackend.dao.reposotories.BookRepo;
import soul.smi.pfe.bookexchangebackend.dao.reposotories.CommentRepo;
import soul.smi.pfe.bookexchangebackend.dao.reposotories.PictureRepo;
import soul.smi.pfe.bookexchangebackend.dao.reposotories.RegisteredUserRepo;
import soul.smi.pfe.bookexchangebackend.dtos.BookDTO;
import soul.smi.pfe.bookexchangebackend.dtos.CommentDTO;
import soul.smi.pfe.bookexchangebackend.dtos.PageInfo;
import soul.smi.pfe.bookexchangebackend.dtos.RegisteredUserDTO;
import soul.smi.pfe.bookexchangebackend.exeptions.UserNotFoundExeption;
import soul.smi.pfe.bookexchangebackend.exeptions.bookNotFoundExeption;
import soul.smi.pfe.bookexchangebackend.mappers.mapping;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional @AllArgsConstructor
public class BookServiceImpl implements BookService {
    private CommentService commentService;
    private RegisteredUserRepo registeredUserRepo;
    private BookRepo bookRepo;
    private mapping mapper;
    private PictureRepo pictureRepo;
    @Override
    public BookDTO findBook(Long bookId) throws bookNotFoundExeption {
        Book book = bookRepo.findById(bookId).orElseThrow(() -> new bookNotFoundExeption("book not found"));
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
    public void addBookToUser(String userId, BookDTO bookDTO, byte[] imageContent) throws  UserNotFoundExeption {
        Book book=new Book();
        book = mapper.fromBookDTO(bookDTO);
        book.setAddingDate(new Date());
        RegisteredUser user = registeredUserRepo.findById(userId).orElseThrow(() -> new UserNotFoundExeption("user not found"));
        book.setOwner(user);
        Picture picture=new Picture();
        picture.setAddingDate(new Date());
        picture.setPictureContent(imageContent);
        picture.setPictureName(book.getBookTitle());
        Picture savedPic = pictureRepo.save(picture);
        book.setBookPicture(savedPic);
        bookRepo.save(book);
    }

    @Override
    public void deleteBook(Long bookId) {
        // we have to remove comment of this book before deleting it cause the comments has a forign key to book
        commentService.deleteAllCommentOfBook(bookId);
        bookRepo.deleteById(bookId);
    }

    @Override
    public void deleteAllBooksOfUser(String userId) {
        getAllBooksOfUser(userId).stream().forEach(bookDTO -> {
            deleteBook(bookDTO.getBookId());
        });
    }


    @Override
    public byte[] getImageOfBook(Long bookId) throws bookNotFoundExeption {
        Book book = bookRepo.findById(bookId).orElseThrow(() -> new bookNotFoundExeption("book not found"));
        return book.getBookPicture().getPictureContent();
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
    public RegisteredUserDTO getOwnerOfBook(Long bookId) throws bookNotFoundExeption {
        RegisteredUser owner  = bookRepo.findById(bookId).orElseThrow(() -> new bookNotFoundExeption("book not found")).getOwner();
        if(owner instanceof AdminUser){
            return mapper.fromAdminUser((AdminUser) owner);
        }else{
            return mapper.fromRegularUser((RegularUser) owner);
        }
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


}
