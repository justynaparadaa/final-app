package pl.jparada.app.finalapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.jparada.app.finalapp.model.Participant;
import pl.jparada.app.finalapp.service.EventService;
import pl.jparada.app.finalapp.service.ParticipantService;


@Controller
@RequestMapping(value = "/api/v1/events")
public class ParticipantController {

    @Autowired
    private EventService eventService;

    @Autowired
    private ParticipantService participantService;

    @PutMapping("/{id}/participants")
    public ResponseEntity<Participant> addParticipant(@RequestBody Participant participant, @PathVariable(value = "id") Long eventId){

        if(!eventService.existParticipant(eventId, participant)) {
            participantService.saveParticipant(participant);
            eventService.addParticipant(eventId, participant);
        } else {
            participant = eventService.getParticipant(eventId, participant);
        }

        return ResponseEntity.ok().body(participant);
    }
}
