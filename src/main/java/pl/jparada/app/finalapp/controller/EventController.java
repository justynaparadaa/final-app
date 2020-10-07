package pl.jparada.app.finalapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jparada.app.finalapp.model.Event;
import pl.jparada.app.finalapp.service.EventService;
import pl.jparada.app.finalapp.service.ParticipantService;
import pl.jparada.app.finalapp.service.PaymentService;

@RestController
@RequestMapping(value = "/api/v1/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping()
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        eventService.saveEvent(event);
        return ResponseEntity.ok()
                .body(event);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable(value = "id") Long id) {
        Event event = eventService.getEventById(id);
        return ResponseEntity.ok().body(event);
    }

}
