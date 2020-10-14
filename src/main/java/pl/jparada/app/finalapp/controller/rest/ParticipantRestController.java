package pl.jparada.app.finalapp.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jparada.app.finalapp.model.Participant;
import pl.jparada.app.finalapp.service.EventService;
import pl.jparada.app.finalapp.service.ParticipantService;


@RestController
@RequestMapping(value = "/api/v1/events")
public class ParticipantRestController {

    @Autowired
    private EventService eventService;

    @Autowired
    private ParticipantService participantService;

    @PutMapping("/{id}/participants")
    public ResponseEntity<Participant> addParticipant(@RequestBody Participant participant, @PathVariable(value = "id") Long eventId){

        if(!eventService.existParticipant(eventId, participant) && participant.getNameParticipant() != null) {
            participantService.saveParticipant(participant);
            eventService.addParticipant(eventId, participant);
        } else {
            participant = eventService.getParticipant(eventId, participant);
        }

        return ResponseEntity.ok().body(participant);
    }
}
