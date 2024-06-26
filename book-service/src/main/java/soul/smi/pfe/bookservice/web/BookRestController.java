package soul.smi.pfe.bookservice.web;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soul.smi.pfe.bookservice.dtos.BookDTO;
import soul.smi.pfe.bookservice.dtos.PageInfo;
import soul.smi.pfe.bookservice.exeptions.BookNotFoundExeption;
import soul.smi.pfe.bookservice.exeptions.UserNotFoundExeption;
import soul.smi.pfe.bookservice.model.TopOwner;
import soul.smi.pfe.bookservice.model.UserEntity;
import soul.smi.pfe.bookservice.service.BookService;


import javax.annotation.security.PermitAll;
import javax.ws.rs.Path;
import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/books" )
//@CrossOrigin(origins = "http://localhost:4200")
public class BookRestController {
    private BookService bookService;
    //totalPage and totalBooks
    @GetMapping("totalNumber")
    public Long getNumberOfBooks(){
        return bookService.getBooksNumber();
    }
    @GetMapping("topOwners/{number}")
    public List<TopOwner> getTopOwners(@PathVariable int number){
        return bookService.getTopOwners(number);
    }
    @GetMapping("/pageInfo")
    public PageInfo getPageInfoAllBook(@RequestParam(name = "size" , defaultValue = "5")int size){
        return bookService.getPageInfo(size);
    }

    @GetMapping("/pageInfo/{keyword}")
    public PageInfo getPageInfoOfSimilarBook(@PathVariable String keyword ,
                                        @RequestParam(name = "size" , defaultValue = "5")int size){
        return bookService.getPageInfoOfSimilarBook(size , keyword);
    }
    @GetMapping("/pageInfo/category/{category}")
    public PageInfo getPageInfoOfSameCategory(@PathVariable String category ,
                                             @RequestParam(name = "size" , defaultValue = "5")int size){
        return bookService.getPageInfoOfSameCategory(category.toUpperCase() , size);
    }
    @GetMapping("/pageInfo/user/{userId}")
    public PageInfo getPageInfoOfUserBooks(@PathVariable String userId ,
                                              @RequestParam(name = "size" , defaultValue = "5")int size){
        return bookService.getPageInfoOfUserBooks(userId , size);
    }


    @GetMapping
    public List<BookDTO> getAllBooks(@RequestParam(name = "page" , defaultValue = "0") int page,
                                     @RequestParam(name = "size" , defaultValue = "5")int size){
        return bookService.getAllBooksPage(page , size);
    }

    @GetMapping("{bookId}")
    public ResponseEntity<BookDTO> getbook(@PathVariable Long bookId){
        try {
            BookDTO book = bookService.findBook(bookId);
            return new ResponseEntity<>(book , HttpStatus.OK);
        } catch (BookNotFoundExeption e) {
            return new ResponseEntity<>(null , HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/title/{title}")
    public List<BookDTO> getBookByTitle(@PathVariable String title ,
                                        @RequestParam(name = "page" , defaultValue = "0") int page,
                                        @RequestParam(name = "size" , defaultValue = "5")int size
    ){
        return bookService.getBookByTitle(title , page , size);
    }

    @GetMapping("keyword/{keyword}")
    public List<BookDTO> getBooksByKeyword(@PathVariable String keyword ,
                                           @RequestParam(name = "page" , defaultValue = "0") int page,
                                           @RequestParam(name = "size" , defaultValue = "5")int size){
        return bookService.getBookByKeyword(keyword , page , size);

    }
    @GetMapping("category/{category}")
    public List<BookDTO> getBookByCategory(@PathVariable String category ,
                                           @RequestParam(name = "page" , defaultValue = "0") int page,
                                           @RequestParam(name = "size" , defaultValue = "5")int size){
        return bookService.getBooksByCategory(category.toUpperCase() , page , size);

    }


    @GetMapping("/user/{userId}")
    public List<BookDTO> getBooksOfUser(@PathVariable String userId ,
                                        @RequestParam(name = "page" , defaultValue = "0") int page,
                                        @RequestParam(name = "size" , defaultValue = "5")int size){
        return bookService.getAllBooksOfUserPage(userId , page , size);
    }

    @GetMapping("{bookId}/owner")
    public UserEntity getOwnerOfBook(@PathVariable Long bookId){
        try {
            return bookService.getOwnerOfBook(bookId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("{userId}")
    public BookDTO addBook(@PathVariable String userId ,
                        @RequestBody BookDTO bookDTO
                        ) throws IOException {
        try {
            return bookService.addBookToUser(userId , bookDTO);
        } catch (BookNotFoundExeption | UserNotFoundExeption e) {
            throw new RuntimeException(e);
        }
    }


    @DeleteMapping("{bookId}")
    public BookDTO deleteBook(@PathVariable Long bookId){
        try {
            return bookService.deleteBook(bookId);
        } catch (BookNotFoundExeption e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping()
    public BookDTO updateBook(@RequestBody BookDTO book){
        try {
            return this.bookService.updateBook(book);
        } catch (BookNotFoundExeption e) {
            throw new RuntimeException(e);
        }
    }


}
