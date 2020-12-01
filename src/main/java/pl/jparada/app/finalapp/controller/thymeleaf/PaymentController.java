package pl.jparada.app.finalapp.controller.thymeleaf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.jparada.app.finalapp.model.Participant;
import pl.jparada.app.finalapp.model.Payment;
import pl.jparada.app.finalapp.service.EventService;
import pl.jparada.app.finalapp.service.ParticipantService;
import pl.jparada.app.finalapp.service.PaymentService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/api/v2/events")
public class PaymentController {

    @Autowired
    private EventService eventService;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/{e_id}/payments")
    public String addPayment(Model model,
                             @Valid @ModelAttribute("payment") Payment payment,
                             @PathVariable(value = "e_id") Long eventId) {

        model.addAttribute("event", eventService.getEventById(eventId));
        model.addAttribute("payment", payment);
        model.addAttribute("event", eventService.getEventById(eventId));
        model.addAttribute("formUlr", "../" + eventId.toString());

        paymentService.savePayment(payment);

        Double expense = payment.getExpense();
        eventService.addPayment(eventId, payment);
        eventService.addExpenseToTotal(eventId, expense);

        List<Participant> paymentParticipants = payment.getParticipantList();
        participantService.addAmountPaid(payment.getOwner().getId(), expense);
        paymentParticipants.forEach(participant -> participantService.addAmountDue(participant.getId(), expense / paymentParticipants.size()));
        paymentParticipants.forEach(Participant::countBalance);
        paymentParticipants.forEach(participant -> participantService.saveParticipant(participant));

        return "redirect:../{e_id}";
    }
}
