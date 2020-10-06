package pl.jparada.app.finalapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jparada.app.finalapp.model.Participant;
import pl.jparada.app.finalapp.model.SinglePayment;
import pl.jparada.app.finalapp.repository.SinglePaymentRepository;

import java.util.List;

@Service
public class SinglePaymentService {

    @Autowired
    private SinglePaymentRepository singlePaymentRepository;

    public SinglePayment saveAndFlushSinglePayment(SinglePayment singlePayment) {
        return singlePaymentRepository.saveAndFlush(singlePayment);
    }

    public double countPartPayment(Double paidAmount, List<Participant> participantList) {
        return paidAmount / participantList.size();
    }

    public SinglePayment findByDescriptionAndExpense(String description, Double expense) {
        return singlePaymentRepository.findByPaymentDescriptionAndExpense(description, expense);
    }

}
