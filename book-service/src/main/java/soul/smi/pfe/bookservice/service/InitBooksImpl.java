package soul.smi.pfe.bookservice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soul.smi.pfe.bookservice.dao.entities.Book;
import soul.smi.pfe.bookservice.dao.enums.BookCategory;
import soul.smi.pfe.bookservice.dao.reposotories.BookRepo;

import java.util.Date;
import java.util.List;
@Service
@Transactional
public class InitBooksImpl implements InitBooks {
    private BookRepo bookRepo;
    private List<String> books = List.of("crime and punishment", "karamazof brothers", "deamons", "spring in action");
    private List<String> categories = List.of("Autobiography", "Biography", "Essays", "NonFictionNovel", "SelfHelp", "Classics", "CRIME", "Fantasy", "Horror", "Humour", "Poetry" , "Plays");
    private List<String> defaultUsersIds = List.of("703a5c6e-14ad-4ab7-84ac-6bd5040cf8cb" , "11833e18-d9ed-4014-9f88-051bee380cfa" , "97dcbe66-db08-4682-89aa-61ddd356ce0a");
    public InitBooksImpl(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Override
    public void initBooks() {


               for (int i = 0; i < 8*3; i++) {
                   Book book = new Book();
                   if(i<8)
                       book.setOwnerId(defaultUsersIds.get(0));
                   else if (i < 8 * 2) {
                       book.setOwnerId(defaultUsersIds.get(1));
                   }
                   else if (i < 8 * 3) {
                       book.setOwnerId(defaultUsersIds.get(2));
                   }
                   book.setBookTitle(books.get((int) (Math.random() * books.size())));
                   book.setAddingDate(new Date());
                   int i1 = (int) (Math.random() * categories.size());
                   book.setBookCategory(BookCategory.valueOf(categories.get(i1).toUpperCase()));
                   book.setDescription("book description book description book description book description book description book description" +
                           "book description book description  ****" + book.getBookTitle());
                   book.setAuthor("the author");
                   book.setBookPictureId(Integer.toUnsignedLong(i+2));
                   Book save = bookRepo.save(book);
               }


    }
}
