package pl.jparada.app.finalapp.controller.thymeleaf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.jparada.app.finalapp.model.Event;
import pl.jparada.app.finalapp.model.Payment;
import pl.jparada.app.finalapp.service.EventService;
import pl.jparada.app.finalapp.service.ParticipantService;
import pl.jparada.app.finalapp.service.PaymentService;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/api/v2")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/name-event")
    public String nameEvent(Model model) {
        Event event = Event.builder().build();
        model.addAttribute("event", event);
        return "event/create-event";
    }

    @PostMapping("/save-event")
    public String saveEvent(@Valid @ModelAttribute("event") Event event, Model model) {
        model.addAttribute("event", event);
        eventService.saveEvent(event);
        StringBuilder stringBuilder = new StringBuilder();
        String formUrl = stringBuilder.append("events/").append(event.getId().toString()).toString();
        model.addAttribute("formUlr", formUrl);
        return "event/save-event";
    }

    @GetMapping("/events/{id}")
    public String getEventById(@PathVariable(value = "id") Long id, Model model) {
        Event event = eventService.getEventById(id);
        Payment payment = Payment.builder().build();


        model.addAttribute("event", event);
        model.addAttribute("participants", event.getParticipants());
        model.addAttribute("payments", event.getPayment());
        model.addAttribute("payment", payment);
        model.addAttribute("settlements", event.getSettlements());

        StringBuilder stringBuilderParticipant = new StringBuilder();
        String formUrlForParticipant = stringBuilderParticipant
                .append(event.getId().toString())
                .append("/participants").toString();
        model.addAttribute("formUlrParticipant", formUrlForParticipant);

        StringBuilder stringBuilderPayment = new StringBuilder();
        String formUrlForPayment = stringBuilderPayment
                .append(event.getId().toString())
                .append("/payments").toString();
        model.addAttribute("formUlrPayment", formUrlForPayment);

        StringBuilder stringBuilderSettlement = new StringBuilder();
        String formUrlForSettlement = stringBuilderSettlement
                .append(event.getId().toString())
                .append("/settlements").toString();
        model.addAttribute("formUlrParticipant", formUrlForSettlement);

        return "event/show-event";
    }


}
