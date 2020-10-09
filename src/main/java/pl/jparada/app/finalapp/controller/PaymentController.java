package pl.jparada.app.finalapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.jparada.app.finalapp.model.Participant;
import pl.jparada.app.finalapp.model.Payment;
import pl.jparada.app.finalapp.service.EventService;
import pl.jparada.app.finalapp.service.ParticipantService;
import pl.jparada.app.finalapp.service.PaymentService;

import java.util.List;

@Controller
@RequestMapping(value = "/api/v1/events")
public class PaymentController {

    @Autowired
    private EventService eventService;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private PaymentService paymentService;

    @PutMapping("/{e_id}/participants/{o_id}/payments")
    public ResponseEntity<Payment> addPayment(@RequestParam(value = "id") List<String> participantsId, @RequestBody Payment payment, @PathVariable(value = "e_id") Long eventId, @PathVariable(value = "o_id") Long ownerId) {

        Participant owner = participantService.getParticipantById(ownerId);
        List<Participant> paymentParticipants = participantService.getPaymentParticipants(participantsId);

        if (eventService.existParticipant(eventId, owner) && eventService.existParticipant(eventId, paymentParticipants)) {
            payment.setOwner(owner);
            payment.setParticipantList(paymentParticipants);
            paymentService.savePayment(payment);

            Double expense = payment.getExpense();
            eventService.addSinglePayment(eventId, payment);
            eventService.addExpenseToTotal(eventId, expense);

            participantService.addAmountPaid(ownerId, expense);
            paymentParticipants.forEach(participant -> participantService.addAmountDue(participant.getId(), expense / paymentParticipants.size()));
        } else {
            //TODO the line below is correct?
            return ResponseEntity.status(HttpStatus.valueOf(" Participants do not belong to event!")).body(new Payment());
        }
        return ResponseEntity.ok().body(payment);
    }
}
