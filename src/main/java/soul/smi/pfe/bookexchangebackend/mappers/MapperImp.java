package soul.smi.pfe.bookexchangebackend.mappers;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import soul.smi.pfe.bookexchangebackend.dao.entities.*;
import soul.smi.pfe.bookexchangebackend.dao.reposotories.PictureRepo;
import soul.smi.pfe.bookexchangebackend.dtos.*;


@Service
public class MapperImp implements Mapper {

    @Override
    public UserEntityDTO fromUserEntity(UserEntity user) {
        UserEntityDTO userDto=new UserEntityDTO();
        byte[] imageData = user.getProfilePic().getPictureContent();
        String base64Image = Base64.encodeBase64String(imageData);
        userDto.setImageContentBase64(base64Image);
        BeanUtils.copyProperties(user , userDto);
        return userDto;
    }

    @Override
    public UserEntity fromUserEntityDTO(UserEntityDTO userDto) {
        UserEntity user=new UserEntity();
        BeanUtils.copyProperties(userDto , user);
        return user;
    }
    // we need to repair the image creation
    @Override
    public BookDTO fromBook(Book book) {
        BookDTO bookTDO =  new BookDTO();
        //getting the image and asign it to stringBase64Image
        byte[] imageData  = book.getBookPicture().getPictureContent();
        String base64Image = Base64.encodeBase64String(imageData);
        bookTDO.setImageContentBase64(base64Image);
        bookTDO.setOwner(fromUserEntity(book.getOwner()));
        BeanUtils.copyProperties(book , bookTDO);
        return bookTDO;
    }

    @Override
    public Book fromBookDTO(BookDTO bookDTO) {
        Book book =  new Book();
        BeanUtils.copyProperties(bookDTO , book);
        return book;
    }

    @Override
    public CommentDTO fromComment(Comment comment) {
        CommentDTO commentDTO=new CommentDTO();
        commentDTO.setOwner(fromUserEntity(comment.getOwner()));
        BeanUtils.copyProperties(comment,commentDTO);
        return commentDTO;
    }

    @Override
    public Comment fromCommentDTO(CommentDTO commentDTO) {
        Comment comment=new Comment();
        comment.setOwner(fromUserEntityDTO(commentDTO.getOwner()));
        BeanUtils.copyProperties(commentDTO , comment);
        return comment;
    }
    @Override
    public Message fromMessageDTO(MessageDTO messageDTO) {
        return null;
    }

    @Override
    public MessageDTO fromMessage(Message message) {
        return null;
    }


    @Override
    public UserAddress fromAddressDTO(AddressDTO addressDTO) {
        UserAddress address=new UserAddress();
        BeanUtils.copyProperties(addressDTO,address);
        return address;
    }

    @Override
    public AddressDTO fromAddress(UserAddress address) {
        AddressDTO addressDTO=new AddressDTO();
        BeanUtils.copyProperties(address,addressDTO);
        return addressDTO;
    }

}
