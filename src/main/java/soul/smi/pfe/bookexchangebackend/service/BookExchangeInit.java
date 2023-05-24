package soul.smi.pfe.bookexchangebackend.service;

import soul.smi.pfe.bookexchangebackend.dao.entities.Picture;

public interface BookExchangeInit {
    void initUsers();
    void initBooks();
    void initComments();
    Picture createBookImage(String name);
}
