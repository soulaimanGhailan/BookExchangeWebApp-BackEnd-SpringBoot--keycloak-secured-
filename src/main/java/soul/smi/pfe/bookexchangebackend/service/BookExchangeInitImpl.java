package soul.smi.pfe.bookexchangebackend.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soul.smi.pfe.bookexchangebackend.dao.entities.*;
import soul.smi.pfe.bookexchangebackend.dao.enums.BookCategory;
import soul.smi.pfe.bookexchangebackend.dao.reposotories.*;
import soul.smi.pfe.bookexchangebackend.dtos.CommentDTO;
import soul.smi.pfe.bookexchangebackend.exeptions.UserNotFoundExeption;
import soul.smi.pfe.bookexchangebackend.exeptions.BookNotFoundExeption;
import soul.smi.pfe.bookexchangebackend.mappers.Mapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class BookExchangeInitImpl implements BookExchangeInit {
    private List<String> cities=List.of("Larache" , "tanger" ,"rabat" ,"kenitra" ,"casa");
    private List<String> fnames = List.of("soulaiman" , "mohammed" , "halima" , "hanan" , "yassine" ,"jamal" ,"hiba" ,"john"
            ,"yassmine" , "james" ,"Anjila");
    private List<String> lnames = List.of("ghailan" , "james" ,"mejdobi" ,"zeroual" ,"Tenaz" ,"bergam");
    private List<String> books = List.of("crime and punishment", "karamazof brothers", "deamons", "spring in action");
    private   List<String> comments = List.of("this is good", "i am intrested", "i want to exchange with you body",
            "i've read this one", "this book looks sridered");
    private List<String> categories = List.of("Autobiography", "Biography", "Essays", "NonFictionNovel", "SelfHelp", "Classics", "CRIME", "Fantasy", "Horror", "Humour", "Poetry" , "Plays");
    private Mapper mapper;
    private UserService userService;
    private  BookService bookService;
    private  CommentService commentService;
    private UserAddressRepo userAddressRepo;
    private  BookRepo bookRepo;
    private  PictureRepo pictureRepo;
    private UserEntityRepo userRepo ;

    private List<UserEntity> users=new ArrayList<>();
    private List<Book> intialBooks = new ArrayList<>();
    public BookExchangeInitImpl(Mapper mapper, UserService userService,
                                BookService bookService, CommentService commentService,
                                UserAddressRepo userAddressRepo, BookRepo bookRepo,
                                PictureRepo pictureRepo, UserEntityRepo userRepo) {
        this.mapper = mapper;
        this.userService = userService;
        this.bookService = bookService;
        this.commentService = commentService;
        this.userAddressRepo=userAddressRepo;
        this.bookRepo = bookRepo;
        this.pictureRepo = pictureRepo;
        this.userRepo = userRepo;
    }

    @Override
    public void initUsers() {
        userRepo.findAll().forEach(user -> {
            user.setBirthday(new Date());
            user.setPhoneNumber(generatePhoneNum());
            user.setAddress(generatedAddress());
            user.setProfilePic(createUserImage(user.getFirstname()));
            UserEntity save = userRepo.save(user);
            users.add(save);
        });
//        for (int i = 0; i < 3; i++) {
//            UserEntity user =new UserEntity();
//            user.setBirthday(new Date());
//            user.setPhoneNumber(generatePhoneNum());
//            user.setFirstname(fnames.get((int)(Math.random()*(fnames.size()-1))));
//            user.setLastname(lnames.get((int)(Math.random()*(lnames.size()-1))));
//            user.setUserId(UUID.randomUUID().toString());
//            user.setJob("student");
//            user.setEmail(user.getFirstname()+"." + user.getLastname() + "@gmail.com");
//            user.setAddress(generatedAddress());
//            user.setProfilePic(userImages.get((int)(Math.random()*5)));
//            UserEntity save = userRepo.save(user);
//            users.add(save);
//        }
    }

    @Override
    public void initBooks() {
      users.stream().forEach(regularUser -> {
            for (int i = 0; i < 8; i++) {
                Book book = new Book();
                book.setBookTitle(books.get((int) (Math.random() * books.size())));
                book.setAddingDate(new Date());
                int i1 = (int) (Math.random() * categories.size());
                book.setBookCategory(BookCategory.valueOf(categories.get(i1).toUpperCase()));
                book.setDescription("book description book description book description book description book description book description" +
                        "book description book description  ****" + book.getBookTitle());
                book.setOwner(regularUser);
                book.setAuthor("the author");
                book.setBookPicture(createBookImage(book.getBookTitle()));
                Book save = bookRepo.save(book);
                intialBooks.add(save);
            }
        });
    }

    @Override
    public void initComments() {
        users.stream().forEach(user -> {
            intialBooks.stream().forEach(book -> {
                try {
                    for (int i = 0; i < 5; i++) {
                        CommentDTO comment = commentService.comment(user.getUserId(), book.getBookId(), generateComment());
                        for (int j = 0; j < 5; j++) {
                            commentService.reply(user.getUserId() , comment.getCommentId() , generateComment() );
                        }
                    }
                } catch (UserNotFoundExeption | BookNotFoundExeption e) {
                    e.printStackTrace();
                }
            });
        });
    }



    @Override
    public Picture createBookImage(String name) {
        try {
            return createImage("initImages/book/bookImages/" , name , 3);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Picture createUserImage(String name) {
        try {
            return createImage("initImages/book/profile/" , name , 2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




    private String generateComment() {
        return (comments.get((int)(Math.random()*comments.size())));
    }

    private UserAddress generatedAddress() {
        UserAddress address=new UserAddress();
        address.setCountry("Morocco");
        address.setCity(cities.get((int)(Math.random()*cities.size())));
        address.setHomeAddress("1111 lot al xxxxxxxxxxxxx "+ address.getCity());
        UserAddress save = userAddressRepo.save(address);
        return  save;
    }

    private String generatePhoneNum() {
        int v = (int)(10000000+Math.random() * 99999999);
        return "06"+v;
    }

    private String generatePass() {
        return ""+(int)(1000+Math.random()*9999);
    }

    private Picture createImage(String pathName , String name , int numberImages)throws IOException{
        File file=new File(pathName+((int)(Math.random()*numberImages)+1)+".jpg");
        Path path= Paths.get(file.toURI());
        byte[] picContent = Files.readAllBytes(path);
        Picture picture1 = new Picture();
        picture1.setPictureContent(picContent);
        picture1.setAddingDate(new Date());
        picture1.setPictureName(name);
        return pictureRepo.save(picture1);
    }
}
