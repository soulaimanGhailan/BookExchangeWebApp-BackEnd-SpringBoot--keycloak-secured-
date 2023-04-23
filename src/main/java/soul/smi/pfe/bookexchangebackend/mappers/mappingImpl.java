package soul.smi.pfe.bookexchangebackend.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import soul.smi.pfe.bookexchangebackend.dao.entities.*;
import soul.smi.pfe.bookexchangebackend.dtos.*;
import java.util.stream.Collectors;

@Service
public class mappingImpl implements mapping {
    @Override
    public BookDTO fromBook(Book book) {
        BookDTO bookTDO =  new BookDTO();
        RegisteredUser owner = book.getOwner();
        if(owner instanceof RegularUser){
            bookTDO.setOwner(fromRegularUser((RegularUser) owner));
        }else if(owner instanceof AdminUser){
            bookTDO.setOwner(fromAdminUser((AdminUser) owner));
        }else throw  new RuntimeException("dto mismatching");
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
//        RegisteredUserDTO owner = bookDTO.getOwner();
//        if(owner instanceof RegularUserDTO){
//            book.setOwner(fromRegularUserDTO((RegularUserDTO) owner));
//        }else if(owner instanceof AdminUserDTO){
//            book.setOwner(fromAdminUserDTO((AdminUserDTO) owner));
//        }else throw  new RuntimeException("dto mismatching");
//        book.setOwner(fromRegularUserDTO((RegularUserDTO) bookDTO.getOwner()));
        BeanUtils.copyProperties(bookDTO , book);
        return book;
    }

    @Override
    public CommentDTO fromComment(Comment comment) {
        CommentDTO commentDTO=new CommentDTO();
        RegisteredUser owner = comment.getOwner();
        if(owner instanceof RegularUser){
            commentDTO.setOwner(fromRegularUser((RegularUser) owner));
        }else if(owner instanceof AdminUser){
            commentDTO.setOwner(fromAdminUser((AdminUser) owner));
        }else throw  new RuntimeException("dto mismatching");
        BeanUtils.copyProperties(comment,commentDTO);
        return commentDTO;
    }

    @Override
    public Comment fromCommentDTO(CommentDTO commentDTO) {
        Comment comment=new Comment();
        RegisteredUserDTO owner = commentDTO.getOwner();
        if(owner instanceof RegularUserDTO){
            comment.setOwner(fromRegularUserDTO((RegularUserDTO) owner));
        }else if(owner instanceof AdminUserDTO){
            comment.setOwner(fromAdminUserDTO((AdminUserDTO) owner));
        }else throw  new RuntimeException("dto mismatching");
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
    public AdminUserDTO  fromAdminUser(AdminUser adminUser) {
        AdminUserDTO adminUserDTO=new AdminUserDTO();
        BeanUtils.copyProperties(adminUser , adminUserDTO);
        return adminUserDTO;
    }
    @Override
    public AdminUser fromAdminUserDTO(AdminUserDTO adminUserDTO) {
        AdminUser adminUser=new AdminUser();
        BeanUtils.copyProperties(adminUserDTO , adminUser);
        return adminUser;
    }
    @Override
    public RegularUserDTO  fromRegularUser(RegularUser regularUser) {
        RegularUserDTO regularUserDTO=new RegularUserDTO();
        BeanUtils.copyProperties(regularUser , regularUserDTO);
        return regularUserDTO;
    }
    @Override
    public RegularUser fromRegularUserDTO(RegularUserDTO regularUserDTO) {
        RegularUser regularUser=new RegularUser();
        BeanUtils.copyProperties(regularUserDTO , regularUser);
        return regularUser;
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
