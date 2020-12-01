package pl.jparada.app.finalapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jparada.app.finalapp.model.Event;
import pl.jparada.app.finalapp.model.Participant;
import pl.jparada.app.finalapp.model.Payment;
import pl.jparada.app.finalapp.repository.PaymentRepository;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public boolean existsParticipant(Event event, Participant participant) {
        return event.getPayment().stream()
                .anyMatch(payment -> payment.getParticipantList().contains(participant) || payment.getOwner().equals(participant));
    }
}
