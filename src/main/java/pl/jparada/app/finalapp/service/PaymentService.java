package pl.jparada.app.finalapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jparada.app.finalapp.model.Participant;
import pl.jparada.app.finalapp.model.Payment;
import pl.jparada.app.finalapp.repository.PaymentRepository;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public double countPartPayment(Double paidAmount, List<Participant> participantList) {
        return paidAmount / participantList.size();
    }

    public Payment findByDescriptionAndExpense(String description, Double expense) {
        return paymentRepository.findByPaymentDescriptionAndExpense(description, expense);
    }

}
