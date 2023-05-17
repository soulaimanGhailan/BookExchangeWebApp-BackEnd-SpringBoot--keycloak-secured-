package soul.smi.pfe.bookexchangebackend.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import soul.smi.pfe.bookexchangebackend.dao.entities.*;
import soul.smi.pfe.bookexchangebackend.dtos.*;
import java.util.stream.Collectors;

@Service
public class MapperImp implements Mapper {
    @Override
    public UserEntityDTO fromUserEntity(UserEntity user) {
        UserEntityDTO userDto=new UserEntityDTO();
        BeanUtils.copyProperties(user , userDto);
        return userDto;
    }

    @Override
    public UserEntity fromUserEntityDTO(UserEntityDTO userDto) {
        UserEntity user=new UserEntity();
        BeanUtils.copyProperties(userDto , user);
        return user;
    }
    @Override
    public BookDTO fromBook(Book book) {
        BookDTO bookTDO =  new BookDTO();
        bookTDO.setOwner(fromUserEntity(book.getOwner()));
        bookTDO.setComments(
                book.getComments().stream()
                        .map(comment -> fromComment(comment))
                        .collect(Collectors.toList())
        );
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
