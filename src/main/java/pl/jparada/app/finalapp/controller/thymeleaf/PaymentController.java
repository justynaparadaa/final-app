package pl.jparada.app.finalapp.controller.thymeleaf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.jparada.app.finalapp.model.Payment;
import pl.jparada.app.finalapp.service.EventService;
import pl.jparada.app.finalapp.service.ParticipantService;
import pl.jparada.app.finalapp.service.PaymentService;

import javax.validation.Valid;

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
    public String addPayment(@Valid @ModelAttribute("payment") Payment payment,
                             @PathVariable(value = "e_id") Long eventId,
                             Model model) {

        model.addAttribute("event", eventService.getEventById(eventId));
        model.addAttribute("payment", payment);
        payment.setId(null);
        paymentService.savePayment(payment);

        model.addAttribute("event", eventService.getEventById(eventId));
        model.addAttribute("formUlr", "../" + eventId.toString());

        return "payment/save-payment";
    }
}
