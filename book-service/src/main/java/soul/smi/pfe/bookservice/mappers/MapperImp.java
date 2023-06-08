package soul.smi.pfe.bookservice.mappers;

import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import soul.smi.pfe.bookservice.dao.entities.Book;
import soul.smi.pfe.bookservice.dtos.BookDTO;
import soul.smi.pfe.bookservice.model.Picture;
import soul.smi.pfe.bookservice.model.UserEntity;
import soul.smi.pfe.bookservice.service.PictureRestClient;
import soul.smi.pfe.bookservice.service.UsersRestClient;


@Service
public class MapperImp implements Mapper {
    private PictureRestClient pictureRestClient;
    private UsersRestClient usersRestClient ;

    public MapperImp(PictureRestClient pictureRestClient, UsersRestClient usersRestClient) {
        this.pictureRestClient = pictureRestClient;
        this.usersRestClient = usersRestClient;
    }

    // we need to repair the image creation
    @Override
    public BookDTO fromBook(Book book) {
        BookDTO bookTDO =  new BookDTO();
        //getting the image and asign it to stringBase64Image
        Picture image = pictureRestClient.getImage(getToken(), book.getBookPictureId());
        bookTDO.setImageContentBase64(image.getPictureContent());
        UserEntity owner = usersRestClient.getUser(getToken(), book.getOwnerId());
        book.setOwner(owner);
        bookTDO.setOwner(owner);
        BeanUtils.copyProperties(book , bookTDO);
        return bookTDO;
    }

    @Override
    public Book fromBookDTO(BookDTO bookDTO) {
        Book book =  new Book();
        BeanUtils.copyProperties(bookDTO , book);
        return book;
    }

    private String getToken(){
        KeycloakSecurityContext context = (KeycloakSecurityContext) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        String token ="bearer "+ context.getTokenString();
        return token;
    }

}
