package pl.jparada.app.finalapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Participant implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameParticipant;

    private double totalAmountPaid;

    private double totalAmountDue;

    @Transient
    private double balance;

    public Participant(String nameParticipant) {
        this.nameParticipant = nameParticipant;
        balance = totalAmountPaid - totalAmountDue;
    }

}
