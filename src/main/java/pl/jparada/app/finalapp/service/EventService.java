package pl.jparada.app.finalapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jparada.app.finalapp.model.Event;
import pl.jparada.app.finalapp.model.Participant;
import pl.jparada.app.finalapp.model.Payment;
import pl.jparada.app.finalapp.repository.EventRepository;

import java.util.Optional;


@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public void saveEvent(Event event) {
        eventRepository.save(event);
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElse(new Event());
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    public void addParticipant(Long id, Participant participant) {
        Event event = getEventById(id);
        event.getParticipants().add(participant);
        eventRepository.save(event);
    }

    public void addSinglePayment(Long id, Payment paymentFromDb) {
        Event event = getEventById(id);
        event.getPayment().add(paymentFromDb);
        saveEvent(event);
    }

    public void addExpenseToTotal(Long id, Double expense) {
        Event event = getEventById(id);
        double totalExpense = event.getTotalExpense() + expense;
        event.setTotalExpense(totalExpense);
        saveEvent(event);
    }

    public boolean existParticipant(Long id, Participant participant) {
        Event event = getEventById(id);
        return event.getParticipants()
                .stream()
                .anyMatch(p -> p.getNameParticipant().equals(participant.getNameParticipant()));
    }

    public Participant getParticipant(Long id, Participant participant) {
        Event event = getEventById(id);
        Optional<Participant> eventParticipant = event.getParticipants()
                .stream()
                .filter(p -> p.getNameParticipant().equals(participant.getNameParticipant()))
                .findFirst();
        return eventParticipant.orElse(new Participant());
    }

}
