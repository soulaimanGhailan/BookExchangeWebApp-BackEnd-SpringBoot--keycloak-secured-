package soul.smi.pfe.bookexchangebackend.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import soul.smi.pfe.bookexchangebackend.dao.entities.Book;
import soul.smi.pfe.bookexchangebackend.dao.entities.Picture;
import soul.smi.pfe.bookexchangebackend.dao.entities.UserAddress;
import soul.smi.pfe.bookexchangebackend.dao.entities.RegularUser;
import soul.smi.pfe.bookexchangebackend.dao.enums.BookCategory;
import soul.smi.pfe.bookexchangebackend.dao.reposotories.BookRepo;
import soul.smi.pfe.bookexchangebackend.dao.reposotories.PictureRepo;
import soul.smi.pfe.bookexchangebackend.dao.reposotories.RegisteredUserRepo;
import soul.smi.pfe.bookexchangebackend.dao.reposotories.UserAddressRepo;
import soul.smi.pfe.bookexchangebackend.dtos.CommentDTO;
import soul.smi.pfe.bookexchangebackend.exeptions.UserNotFoundExeption;
import soul.smi.pfe.bookexchangebackend.exeptions.bookNotFoundExeption;
import soul.smi.pfe.bookexchangebackend.mappers.mapping;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
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
    private  mapping mapper;
    private  UseresService useresService;
    private  BookService bookService;
    private  CommentService commentService;
    private  RegisteredUserRepo registeredUserRepo;
    private UserAddressRepo userAddressRepo;
    private  BookRepo bookRepo;
    private  PictureRepo pictureRepo;

    private List<RegularUser> regularUsers=new ArrayList<>();
    private List<Book> intialBooks = new ArrayList<>();
    private List<Picture> userImages = new ArrayList<>();
    private List<Picture> bookImages=new ArrayList<>();
    public BookExchangeInitImpl(mapping mapper, UseresService useresService,
                                BookService bookService, CommentService commentService,
                                RegisteredUserRepo registeredUserRepo,
                                UserAddressRepo userAddressRepo, BookRepo bookRepo,
                                PictureRepo pictureRepo) {
        this.mapper = mapper;
        this.useresService = useresService;
        this.bookService = bookService;
        this.commentService = commentService;
        this.registeredUserRepo = registeredUserRepo;
        this.userAddressRepo=userAddressRepo;
        this.bookRepo = bookRepo;
        this.pictureRepo = pictureRepo;
    }

    @Override
    public void initRegisteredUseres() {
        for (int i = 0; i < 3; i++) {
            RegularUser user = new RegularUser();
            user.setUserPassword(generatePass());
            user.setFirstname(fnames.get((int)(Math.random()*(fnames.size()-1))));
            user.setLastname(lnames.get((int)(Math.random()*(lnames.size()-1))));
            user.setUserId(UUID.randomUUID().toString());
            user.setJob("student");
            user.setEmail(user.getFirstname()+"." + user.getLastname() + "@gmail.com");
            user.setBirthday(new Date());
            user.setPhoneNumber(generatePhoneNum());
            user.setAddress(generatedAddress());
            user.setProfilePic(userImages.get((int)(Math.random()*5)));
            RegularUser save = registeredUserRepo.save(user);
            regularUsers.add(save);
        }
    }

    @Override
    public void initBooks() {

      regularUsers.stream().forEach(regularUser -> {
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
                book.setBookPicture(bookImages.get((int)(Math.random()*5)));
                Book save = bookRepo.save(book);
                intialBooks.add(save);
            }
        });

    }

    @Override
    public void initComment() {
        regularUsers.stream().forEach(regularUser -> {
            intialBooks.stream().forEach(book -> {
                try {
                    for (int i = 0; i < 5; i++) {
                        CommentDTO comment = commentService.comment(regularUser.getUserId(), book.getBookId(), generateComment());
                        for (int j = 0; j < 5; j++) {
                            commentService.reply(regularUser.getUserId() , comment.getCommentId() , generateComment() );
                        }
                    }
                } catch (UserNotFoundExeption | bookNotFoundExeption e) {
                    e.printStackTrace();
                }

            });

        });
    }

    @Override
    public void initUserImages() {
        for (int i = 0; i < 5; i++) {
//            File file=new File(System.getProperty("user.home")+"/book/profile/"+((int)(Math.random()*2)+1)+".jpg");
            File file=new File("initImages/book/profile/"+((int)(Math.random()*2)+1)+".jpg");
            Path path= Paths.get(file.toURI());
            try {
                byte[] picContent = Files.readAllBytes(path);
                Picture picture1 = new Picture();
                picture1.setPictureContent(picContent);
                picture1.setAddingDate(new Date());
                picture1.setPictureName("pic num" +i);
                Picture savedPic1 = pictureRepo.save(picture1);
                userImages.add(savedPic1);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void initBookImages () {
        for (int i = 0; i < 5; i++) {
//            File file=new File(System.getProperty("user.home")+"/book/bookImages/"+((int)(Math.random()*3)+1)+".jpg");
            File file=new File("initImages/book/bookImages/"+((int)(Math.random()*3)+1)+".jpg");
            Path path= Paths.get(file.toURI());
            try {
                byte[] picContent = Files.readAllBytes(path);
                Picture picture1 = new Picture();
                picture1.setPictureContent(picContent);
                picture1.setAddingDate(new Date());
                picture1.setPictureName("book num" + i);
                Picture savedPic1 = pictureRepo.save(picture1);
                bookImages.add(savedPic1);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
}
