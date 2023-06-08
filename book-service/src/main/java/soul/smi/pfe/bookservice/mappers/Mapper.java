package soul.smi.pfe.bookservice.mappers;


import soul.smi.pfe.bookservice.dao.entities.Book;
import soul.smi.pfe.bookservice.dtos.BookDTO;

public interface Mapper {
    BookDTO fromBook(Book book);
    Book fromBookDTO(BookDTO bookDTO);
}
