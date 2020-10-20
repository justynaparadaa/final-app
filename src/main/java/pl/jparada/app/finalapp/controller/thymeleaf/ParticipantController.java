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
    public String addParticipant(Model model, @PathVariable(name = "event_id") Long eventId) {
        Participant participant = Participant.builder().build();
        model.addAttribute("participant", participant);
        model.addAttribute("event", eventService.getEventById(eventId));
        model.addAttribute("formUlr", "");
        return "participant/create-participant";
    }

    @PostMapping("/{event_id}/participants")
    public String saveParticipant(Model model,
                                  @Valid @ModelAttribute("participant") Participant participant,
                                  @PathVariable(value = "event_id") Long eventId) {
        model.addAttribute("participant", participant);

        if (!eventService.existParticipant(eventId, participant) && participant.getNameParticipant() != null) {
            participantService.saveParticipant(participant);
            eventService.addParticipant(eventId, participant);
        }

        model.addAttribute("event", eventService.getEventById(eventId));
        model.addAttribute("formUlr", "../" + eventId.toString());

        return "participant/save-participant";
    }

    //TODO
    @GetMapping("/{event_id}/participants/{part_Id}/delete")
    public String deleteParticipant(Model model,
                                    @PathVariable(value = "event_id") Long eventId,
                                    @PathVariable(value = "event_id") Long partId) {

        participantService.delete(partId);
        model.addAttribute("formUlr", "../" + eventId.toString());
        return "participant/delete-participant";
    }

}
