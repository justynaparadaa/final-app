package pl.jparada.app.finalapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jparada.app.finalapp.model.Participant;
import pl.jparada.app.finalapp.repository.ParticipantRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;

    public void saveParticipant(Participant participant) {
        participantRepository.save(participant);
    }

    public Participant getParticipantById(Long id) {
        return participantRepository.findById(id).orElse(new Participant());
    }

    public void addAmountPaid(Long ownerId, Double paidAmount) {
        Participant participantById = getParticipantById(ownerId);
        double initialPaidAmount = participantById.getTotalAmountPaid();
        double totalPaidAmount = initialPaidAmount + paidAmount;
        participantById.setTotalAmountPaid(totalPaidAmount);
        saveParticipant(participantById);
    }

    public void addAmountDue(Long participantId, Double partPayment) {
        Participant participantById = getParticipantById(participantId);
        double initialDueAmount = participantById.getTotalAmountDue();
        double totalPaidAmount = initialDueAmount + partPayment;
        participantById.setTotalAmountDue(totalPaidAmount);
        saveParticipant(participantById);

    }

    public List<Participant> getAllParticipants() {
        return participantRepository.findAll();
    }

    public List<Participant> getPaymentParticipants(List<String> participantIds) {
        ArrayList<Long> participantIdsLong = new ArrayList<>();
        ArrayList<Participant> paymentParticipants = new ArrayList<>();
        for (String s : participantIds) {
            long participantId = Long.parseLong(s);
            participantIdsLong.add(participantId);
        }
        for (Long id : participantIdsLong) {
            paymentParticipants.add(getParticipantById(id));
        }
        return paymentParticipants;
    }
}
