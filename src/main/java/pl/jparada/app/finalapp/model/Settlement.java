package pl.jparada.app.finalapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Settlement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Participant debtor;

    @OneToOne
    private Participant lender;

//    @Transient
//    private Map<Long, Double> debtorMap;


    private double amount;

    public Settlement(Participant debtor, Participant lender, double amount) {
        this.lender = lender;
        this.debtor = debtor;
        this.amount = amount;
    }
}
