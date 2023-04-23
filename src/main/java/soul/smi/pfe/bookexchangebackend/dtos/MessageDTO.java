package soul.smi.pfe.bookexchangebackend.dtos;


import lombok.Data;
import soul.smi.pfe.bookexchangebackend.dao.entities.RegisteredUser;
import soul.smi.pfe.bookexchangebackend.dao.enums.MessageStatus;
@Data
public class MessageDTO {
    private Long messageId;
    private String messageContent;
    private MessageStatus status;
    private RegisteredUserDTO sender;
    private RegisteredUserDTO reciever;
}
