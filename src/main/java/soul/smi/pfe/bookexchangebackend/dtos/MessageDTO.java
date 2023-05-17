package soul.smi.pfe.bookexchangebackend.dtos;


import lombok.Data;
import soul.smi.pfe.bookexchangebackend.dao.enums.MessageStatus;
@Data
public class MessageDTO {
    private Long messageId;
    private String messageContent;
    private MessageStatus status;
    private UserEntityDTO sender;
    private UserEntityDTO reciever;
}
