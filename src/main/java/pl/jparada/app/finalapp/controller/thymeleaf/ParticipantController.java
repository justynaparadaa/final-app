package pl.jparada.app.finalapp.controller.thymeleaf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.jparada.app.finalapp.model.Participant;
import pl.jparada.app.finalapp.service.EventService;
import pl.jparada.app.finalapp.service.ParticipantService;

import javax.validation.Valid;


@Controller
@RequestMapping(value = "/api/v2/events")
public class ParticipantController {

    @Autowired
    private EventService eventService;

    @Autowired
    private ParticipantService participantService;

    @GetMapping("/{event_id}/participants")
    public String addParticipant(Model model, @PathVariable(value = "event_id") Long eventId) {
        Participant participant = Participant.builder().build();
        model.addAttribute("participant", participant);
        model.addAttribute("event", eventService.getEventById(eventId));
        model.addAttribute("formUlr", "");
        return "participant/create-participant";
    }

    @PostMapping("/{id}/participants")
    public String saveParticipant(@Valid @ModelAttribute("participant") Participant participant,
                                  @PathVariable(value = "id") Long eventId,
                                  Model model) {
        model.addAttribute("participant", participant);
        //TODO how avoid the line below
        participant.setId(null);

        if (!eventService.existParticipant(eventId, participant)) {
            participantService.saveParticipant(participant);
            eventService.addParticipant(eventId, participant);
        }

        model.addAttribute("event", eventService.getEventById(eventId));
        model.addAttribute("formUlr", "../" + eventId.toString());

        return "participant/save-participant";
    }
}
