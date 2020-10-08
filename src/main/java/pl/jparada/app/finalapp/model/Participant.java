package pl.jparada.app.finalapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Comparator;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Participant implements Serializable, Comparable<Participant>, Comparator<Participant> {

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
    }

    public void countBalance() {
        balance = this.getTotalAmountPaid() - this.getTotalAmountDue();
    }

    @Override
    public int compareTo(Participant participant) {
        return (int) (this.getBalance() - participant.getBalance());
    }

    @Override
    public int compare(Participant participant, Participant t1) {
        return (int) (participant.getBalance() - t1.getBalance());
    }
}
