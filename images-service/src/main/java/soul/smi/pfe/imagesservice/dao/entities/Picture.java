package soul.smi.pfe.imagesservice.dao.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Picture {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date addingDate;
    private String pictureName;
    @Column(length = 1000000)
    @Lob
    private byte[] pictureContent;
}
