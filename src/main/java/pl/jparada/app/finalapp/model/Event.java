package pl.jparada.app.finalapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameEvent;

    @OneToMany
    @JoinColumn(name = "event_id")
    private List<Participant> participants;

    @OneToMany
    @JoinColumn(name = "event_id")
    private List<SinglePayment> singlePayment;

    private double totalExpense;


    public Event(String nameEvent) {
        this.nameEvent = nameEvent;
    }

    public Event(String nameEvent, List<Participant> participants) {
        this.nameEvent = nameEvent;
        this.participants = participants;
    }
}
