package soul.smi.pfe.bookexchangebackend.dao.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
