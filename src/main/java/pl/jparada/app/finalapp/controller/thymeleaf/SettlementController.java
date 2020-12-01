package pl.jparada.app.finalapp.controller.thymeleaf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{event_id}/settlements")
    public String makeReconciliation (@PathVariable(value = "event_id") Long eventId) {

        Event event = eventService.getEventById(eventId);
        List<Participant> participants = event.getParticipants();
        List<Settlement> previousSettlements = new ArrayList<>();

        if(eventService.existSettlement(eventId)){
            previousSettlements = event.getSettlements();
        }
        List<Settlement> settlements = settlementService.makeAndSaveSettlement(participants);
        event.setSettlements(settlements);
        eventService.saveEvent(event);
        settlementService.deleteSettlementsById(previousSettlements);

        return "participant/save-participant";
    }
}
