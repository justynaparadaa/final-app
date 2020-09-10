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

        Event eventById = eventService.getEventById(eventId);
        Participant ownerById = participantService.getParticipantById(ownerId);
        Double expense = singlePayment.getExpense();

        singlePayment.setOwner(ownerById);
        singlePaymentService.saveSinglePayment(singlePayment);
        SinglePayment singlePaymentFromDb = singlePaymentService.findByDescriptionAndAmountPaid(singlePayment.getPaymentDescription(), expense);

        eventService.addSinglePayment(eventById, singlePaymentFromDb);
        eventService.addExpenseToTotal(eventById, expense);

        return ResponseEntity.ok().body(singlePaymentFromDb);
    }


//    1) Payment zapisany w bazie z ownerem bez uczestników
//    2) Payment dopisany do eventu
//    3) Total koszt zapisany do Eventu
}
