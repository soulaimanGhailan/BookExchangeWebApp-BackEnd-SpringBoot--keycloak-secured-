package soul.smi.pfe.ChatComservice.dao.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import soul.smi.pfe.ChatComservice.dao.enums.ReceivingStatus;
import soul.smi.pfe.ChatComservice.dao.enums.NotificationType;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Notification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private ReceivingStatus status;
    @Enumerated(EnumType.STRING)
    private NotificationType type;
    private Long BookId ;
    private String receiverId;
}
