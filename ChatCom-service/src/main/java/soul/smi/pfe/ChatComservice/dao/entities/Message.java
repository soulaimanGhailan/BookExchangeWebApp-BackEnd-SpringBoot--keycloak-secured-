package soul.smi.pfe.ChatComservice.dao.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import soul.smi.pfe.ChatComservice.dao.enums.ReceivingStatus;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor @NoArgsConstructor
@Entity
public class Message {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId ;
    private String messageContent ;
    private Date sendingDate ;
    private String senderId ;
    private String receiverId  ;
    @Enumerated(EnumType.STRING)
    private ReceivingStatus status ;

}
