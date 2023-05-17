package soul.smi.pfe.bookexchangebackend.dao.entities;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import soul.smi.pfe.bookexchangebackend.dao.enums.MessageStatus;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
public class Message {
    @Id
    private Long messageId;
    private String messageContent;
    private MessageStatus status;
    @ManyToOne
    @NonNull
    private UserEntity sender;
    @ManyToOne@NonNull
    private UserEntity reciever;
}
