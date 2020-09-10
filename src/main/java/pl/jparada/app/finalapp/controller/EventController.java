package pl.jparada.app.finalapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jparada.app.finalapp.model.Event;
import pl.jparada.app.finalapp.model.Participant;
import pl.jparada.app.finalapp.model.PaymentParticipants;
import pl.jparada.app.finalapp.model.SinglePayment;
import pl.jparada.app.finalapp.service.EventService;
import pl.jparada.app.finalapp.service.ParticipantService;
import pl.jparada.app.finalapp.service.PaymentParticipantsService;
import pl.jparada.app.finalapp.service.SinglePaymentService;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/api/v1/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private SinglePaymentService singlePaymentService;

    @Autowired
    private PaymentParticipantsService paymentParticipantsService;

    @PostMapping()
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        eventService.saveEvent(event);
        return ResponseEntity.ok()
                .body(event);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable(value = "id") Long id) {
        Event eventById = eventService.getEventById(id);
        return ResponseEntity.ok().body(eventById);
    }

}
