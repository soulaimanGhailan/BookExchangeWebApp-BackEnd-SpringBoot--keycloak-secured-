package soul.smi.pfe.bookexchangebackend.mappers;

import soul.smi.pfe.bookexchangebackend.dao.entities.*;
import soul.smi.pfe.bookexchangebackend.dtos.*;

public interface mapping {
    BookDTO fromBook(Book book);
    Book fromBookDTO(BookDTO bookDTO);
    CommentDTO fromComment(Comment comment);
    Comment fromCommentDTO(CommentDTO commentDTO);
    Message fromMessageDTO(MessageDTO messageDTO);
    MessageDTO fromMessage(Message message);
    AdminUserDTO  fromAdminUser(AdminUser adminUser);
    AdminUser fromAdminUserDTO(AdminUserDTO adminUserDTO);
    RegularUserDTO  fromRegularUser(RegularUser regularUser);
    RegularUser fromRegularUserDTO(RegularUserDTO regularUserDTO);
    UserAddress fromAddressDTO(AddressDTO addressDTO);
    AddressDTO fromAddress(UserAddress address);
}
