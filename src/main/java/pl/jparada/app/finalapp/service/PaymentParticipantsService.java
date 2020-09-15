package pl.jparada.app.finalapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jparada.app.finalapp.model.PaymentParticipants;
import pl.jparada.app.finalapp.repository.PaymentParticipantsRepository;

@Service
public class PaymentParticipantsService {

    @Autowired
    private PaymentParticipantsRepository paymentParticipantsRepository;

    public void savePaymentParticipants(PaymentParticipants paymentParticipants) {
        paymentParticipantsRepository.save(paymentParticipants);
    }

//    @Autowired
//    private PaymentParticipantsRepository paymentParticipantsRepository;

//    public Set<PaymentParticipants> savePaymentParticipants(List<PaymentParticipants> paymentParticipants) {
//
//        Iterable<PaymentParticipants> participants = this.paymentParticipantsRepository.saveAll(paymentParticipants);
//
//        List<PaymentParticipants> created = StreamSupport.stream(participants.spliterator(), false)
//                .collect(Collectors.toList());
//
//        return created;
//    }


}
