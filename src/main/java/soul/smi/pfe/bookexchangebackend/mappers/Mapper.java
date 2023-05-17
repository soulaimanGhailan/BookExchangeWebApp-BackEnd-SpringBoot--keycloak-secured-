package soul.smi.pfe.bookexchangebackend.mappers;

import soul.smi.pfe.bookexchangebackend.dao.entities.*;
import soul.smi.pfe.bookexchangebackend.dtos.*;

public interface Mapper {
    BookDTO fromBook(Book book);
    Book fromBookDTO(BookDTO bookDTO);
    CommentDTO fromComment(Comment comment);
    Comment fromCommentDTO(CommentDTO commentDTO);
    Message fromMessageDTO(MessageDTO messageDTO);
    MessageDTO fromMessage(Message message);
    UserAddress fromAddressDTO(AddressDTO addressDTO);
    AddressDTO fromAddress(UserAddress address);
    UserEntityDTO fromUserEntity(UserEntity user);
    UserEntity fromUserEntityDTO(UserEntityDTO user);
}
