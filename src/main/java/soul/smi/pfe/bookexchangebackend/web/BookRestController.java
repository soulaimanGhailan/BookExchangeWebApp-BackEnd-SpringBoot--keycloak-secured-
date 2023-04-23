package soul.smi.pfe.bookexchangebackend.web;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import soul.smi.pfe.bookexchangebackend.dtos.BookDTO;
import soul.smi.pfe.bookexchangebackend.dtos.PageInfo;
import soul.smi.pfe.bookexchangebackend.dtos.RegisteredUserDTO;
import soul.smi.pfe.bookexchangebackend.exeptions.UserNotFoundExeption;
import soul.smi.pfe.bookexchangebackend.exeptions.bookNotFoundExeption;
import soul.smi.pfe.bookexchangebackend.service.BookService;

import java.io.IOException;
import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping("/book")
@CrossOrigin(origins = "http://localhost:4200")
public class BookRestController {
    private BookService bookService;
    //totalPage and totalBooks
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
    public List<BookDTO> getAllBooks(  @RequestParam(name = "page" , defaultValue = "0") int page,
                                       @RequestParam(name = "size" , defaultValue = "5")int size){
        return bookService.getAllBooksPage(page , size);
    }
    @ResponseBody
    @GetMapping(path = "/{bookId}/image" , produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathVariable Long bookId){
        try {
            return bookService.getImageOfBook(bookId);
        } catch (bookNotFoundExeption e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("{bookId}")
    public ResponseEntity<BookDTO> getbook(@PathVariable Long bookId){
        try {
            BookDTO book = bookService.findBook(bookId);
            return new ResponseEntity<>(book , HttpStatus.OK);
        } catch (bookNotFoundExeption e) {
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
    public RegisteredUserDTO getOwnerOfBook(@PathVariable Long bookId){
        try {
            return bookService.getOwnerOfBook(bookId);
        } catch (bookNotFoundExeption e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("{userId}")
    public void addBook(@PathVariable String userId ,
                        @RequestBody BookDTO bookDTO,
                        @RequestPart(value = "file", required = true) MultipartFile file
                        ) throws IOException {

//            byte[] imageContent = file.getBytes();
             if (file.isEmpty()) {
                  throw new IllegalArgumentException("File is required");
              }
//              if (!file.getContentType().equals(MediaType.IMAGE_JPEG_VALUE)) {
//                  throw new IllegalArgumentException("Only PNG images are allowed");
//              }
                   byte[] imageContent = file.getBytes();

            try {
                bookService.addBookToUser(userId , bookDTO , imageContent);
            } catch (bookNotFoundExeption | UserNotFoundExeption e) {
                throw new RuntimeException(e);
            }
    }

    @DeleteMapping("{userId}")
    public void deleteAllBooksOfUser(@PathVariable String userId){
        bookService.deleteAllBooksOfUser(userId);
    }

}
