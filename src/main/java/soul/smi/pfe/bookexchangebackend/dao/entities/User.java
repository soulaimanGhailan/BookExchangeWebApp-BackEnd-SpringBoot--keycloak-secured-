package soul.smi.pfe.bookexchangebackend.dao.entities;

import lombok.Data;

import java.util.Collection;


@Data
public class User {
private Collection<Book> bookHistory;
}
