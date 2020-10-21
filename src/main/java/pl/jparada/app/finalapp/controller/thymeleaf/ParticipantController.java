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

    //TODO how to delete participant?
    @GetMapping("/{event_id}/participants/{part_Id}/delete")
    public String deleteParticipant(Model model,
                                    @PathVariable(value = "event_id") Long eventId,
                                    @PathVariable(value = "event_id") Long partId) {

        participantService.delete(partId);
        model.addAttribute("formUlr", "../" + eventId.toString());
        return "participant/delete-participant";
    }

    @GetMapping("/{event_id}/participants/{part_id}")
    public String getParticipantToEdit(Model model,
                                       @PathVariable(value = "event_id") Long eventId,
                                       @PathVariable(value = "part_id") Long partId) {

        Participant participant = participantService.getParticipantById(partId);
        model.addAttribute("participant", participant);
        model.addAttribute("participantName", participant.getNameParticipant());
        model.addAttribute("event", eventService.getEventById(eventId));

        return "participant/edit-participant";
    }

    @PostMapping("/{event_id}/participants/{part_id}")
    public String editParticipantName(Model model,
                                  @PathVariable(value = "event_id") Long eventId,
                                  @PathVariable(value = "part_id") Long partId,
                                  @Valid @ModelAttribute("participant") Participant participant) {

        Participant participantFrDB = participantService.getParticipantById(partId);
        participantFrDB.setNameParticipant(participant.getNameParticipant());
        participantService.saveParticipant(participantFrDB);

        //TODO more about redirect want to know
        return "redirect:../../{event_id}";
    }


}
