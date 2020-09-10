package pl.jparada.app.finalapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jparada.app.finalapp.model.Event;
import pl.jparada.app.finalapp.model.Participant;
import pl.jparada.app.finalapp.model.SinglePayment;
import pl.jparada.app.finalapp.repository.EventRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public void saveEvent(Event event) {
        eventRepository.save(event);
    }

    public Event getEventById(Long id) {
        //Optional<Event> eventById = eventRepository.findById(id);
        //Event event = eventById.orElseGet(() -> new Event());  ; // jeżeli Optional będzię miał naprawde Eventa to zwróci Event, jak będzie null to zwrócili nowy pusty Event też
        return eventRepository.findById(id).orElse(new Event());
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    public void addEventParticipant(Event eventById, Participant participant) {

        if (eventById.getParticipants()
                .stream()
                .noneMatch(e -> e.getNameParticipant().equals(participant.getNameParticipant())))
            eventById.getParticipants().add(participant);

        saveEvent(eventById);
    }

    public void addSinglePayment(Event eventById, SinglePayment singlePaymentFromDb) {

        if(eventById.getSinglePayment()
                .stream()
                .noneMatch(e -> e.getPaymentDescription().equals(singlePaymentFromDb.getPaymentDescription())))
        eventById.getSinglePayment().add(singlePaymentFromDb);

        saveEvent(eventById);
    }

    public void addExpenseToTotal(Event eventById, Double expense) {
        double totalExpense = eventById.getTotalExpense() + expense;
        eventById.setTotalExpense(totalExpense);
        saveEvent(eventById);
    }
}
