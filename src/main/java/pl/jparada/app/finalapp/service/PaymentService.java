package pl.jparada.app.finalapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jparada.app.finalapp.model.Participant;
import pl.jparada.app.finalapp.model.Payment;
import pl.jparada.app.finalapp.repository.PaymentRepository;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment savePayment(Payment payment) {
        payment.getParticipantList().forEach(Participant::countBalance);
        return paymentRepository.save(payment);
    }

}
