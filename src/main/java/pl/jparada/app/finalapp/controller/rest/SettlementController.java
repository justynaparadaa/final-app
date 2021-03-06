package pl.jparada.app.finalapp.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.jparada.app.finalapp.model.Event;
import pl.jparada.app.finalapp.model.Participant;
import pl.jparada.app.finalapp.model.Settlement;
import pl.jparada.app.finalapp.service.EventService;
import pl.jparada.app.finalapp.service.ParticipantService;
import pl.jparada.app.finalapp.service.SettlementService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/api/v1/events")
public class SettlementController {

    @Autowired
    private SettlementService settlementService;

    @Autowired
    private EventService eventService;

    @Autowired
    private ParticipantService participantService;

    @PostMapping("{id}/settlement")
    public ResponseEntity<List<Settlement>> makeSettlement(@PathVariable(value = "id") Long eventId) {

        Event event = eventService.getEventById(eventId);
        List<Participant> participants = event.getParticipants();
        List<Settlement> previousSettlements = new ArrayList<>();

        if(eventService.existSettlement(eventId)){
            previousSettlements = event.getSettlement();
        }
        List<Settlement> settlements = settlementService.makeAndSaveSettlement(participants);
        event.setSettlement(settlements);
        eventService.saveEvent(event);
        settlementService.deleteSettlementsById(previousSettlements);

        return ResponseEntity.ok().body(settlements);
    }
}
