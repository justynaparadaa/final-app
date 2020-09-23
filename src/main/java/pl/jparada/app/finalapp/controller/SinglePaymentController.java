package pl.jparada.app.finalapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.jparada.app.finalapp.model.Event;
import pl.jparada.app.finalapp.model.Participant;
import pl.jparada.app.finalapp.model.SinglePayment;
import pl.jparada.app.finalapp.service.EventService;
import pl.jparada.app.finalapp.service.ParticipantService;
import pl.jparada.app.finalapp.service.SinglePaymentService;

import java.util.ArrayList;
import java.util.List;

//import pl.jparada.app.finalapp.service.EventParticipantsService;

@Controller
@RequestMapping(value = "/api/v1/events")
public class SinglePaymentController {

    @Autowired
    private EventService eventService;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private SinglePaymentService singlePaymentService;

    @PutMapping("/{e_id}/participants/{o_id}/payments")
    public ResponseEntity<SinglePayment> addSinglePayment(@RequestBody SinglePayment singlePayment, @PathVariable(value = "e_id") Long eventId, @PathVariable(value = "o_id") Long ownerId){

        Event event = eventService.getEventById(eventId);
        Participant owner = participantService.getParticipantById(ownerId);
        Double expense = singlePayment.getExpense();
        List<Participant> paymentParticipants = new ArrayList<>(event.getParticipants());

        singlePayment.setOwner(owner);
        singlePayment.setParticipantList(paymentParticipants);
        singlePaymentService.saveSinglePayment(singlePayment);
        SinglePayment singlePaymentFromDb = singlePaymentService.findByDescriptionAndExpense(singlePayment.getPaymentDescription(), expense);

        eventService.addSinglePayment(event, singlePaymentFromDb);
        eventService.addExpenseToTotal(event, expense);

        participantService.addAmountPaid(ownerId, expense);
        paymentParticipants.forEach(participant -> participantService.addAmountDue(participant.getId(), expense/paymentParticipants.size()));

        return ResponseEntity.ok().body(singlePaymentFromDb);
    }


//    1) Payment zapisany w bazie z ownerem bez uczestników
//    2) Payment dopisany do eventu
//    3) Total koszt zapisany do Eventu
}
