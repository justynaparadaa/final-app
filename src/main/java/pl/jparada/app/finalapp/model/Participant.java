package pl.jparada.app.finalapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Comparator;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Participant implements Serializable, Comparator<Participant> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameParticipant;

    private double totalAmountPaid;

    private double totalAmountDue;

    private double balance;

    private boolean deleted;

    public Participant(String nameParticipant) {
        this.nameParticipant = nameParticipant;
    }

    //TODO refactor method? making it via service?!
    public void countBalance() {
        long round = Math.round(((this.getTotalAmountPaid() - this.getTotalAmountDue()) * 100));
        double balance = (double) round/100;
        setBalance(balance);
    }

    @Override
    public int compare(Participant participant, Participant t1) {
        return (int) (participant.getBalance() - t1.getBalance());
    }
}
