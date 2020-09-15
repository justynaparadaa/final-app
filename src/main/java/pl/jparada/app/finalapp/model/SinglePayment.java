package pl.jparada.app.finalapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SinglePayment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paymentDescription;

    private Double expense;

    @ManyToOne
    private Participant owner;

    @OneToOne
    private PaymentParticipants paymentParticipants;


}
