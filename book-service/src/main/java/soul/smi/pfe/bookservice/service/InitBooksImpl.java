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
    private List<String> books = List.of("crime and punishment",  "spring in action" , "the alchemist");
    private List<String> categories = List.of("Autobiography", "Biography", "Essays", "NonFictionNovel", "SelfHelp", "Classics", "CRIME", "Fantasy", "Horror", "Humour", "Poetry" , "Plays");
    private List<String> defaultUsersIds = List.of("c92fb040-0719-4c15-a364-57e8e514d2c2" , "87d7a13c-d609-4557-8e95-df2a80870479" , "f6b6d8b9-b735-4eb3-b90b-6a8a76918823");
    public InitBooksImpl(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Override
    public void initBooks() {

        int r = 0 ; int l=0;
               for (int i = 0; i < 8; i++) {
                   if(r<8) l = 0;
                   else if(r<8*2) l = 1;
                   else if(r<8*3) l = 2;
                   createBook(defaultUsersIds.get(0) , Integer.toUnsignedLong(r++ + 2) , books.get(l));
                   createBook(defaultUsersIds.get(1) , Integer.toUnsignedLong(r++ + 2) , books.get(l));
                   createBook(defaultUsersIds.get(2) , Integer.toUnsignedLong(r++ + 2) , books.get(l));

               }
    }
    private void createBook(String userId , Long picId , String title){
        Book book = new Book();
        book.setOwnerId(userId);
        book.setBookTitle(title);
        book.setAddingDate(new Date());
        int i1 = (int) (Math.random() * categories.size());
        book.setBookCategory(BookCategory.valueOf(categories.get(i1).toUpperCase()));
        book.setDescription("book description book description book description book description book description book description" +
                "book description book description  ****" + book.getBookTitle());
        book.setAuthor("the author");
        book.setBookPictureId(picId);
        Book save = bookRepo.save(book);
    }
}
