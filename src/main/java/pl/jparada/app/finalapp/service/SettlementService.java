package pl.jparada.app.finalapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jparada.app.finalapp.model.Participant;
import pl.jparada.app.finalapp.model.Settlement;
import pl.jparada.app.finalapp.repository.SettlementRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SettlementService {

    @Autowired
    private SettlementRepository settlementRepository;

    public List<Settlement> makeAndSaveSettlement(List<Participant> participants) {


        participants.forEach(Participant::countBalance);

        List<Participant> participantsToSettlement = new ArrayList<>(participants);
        List<Settlement> settlements = new ArrayList<>();

        while (!participantsToSettlement.isEmpty()) {
            List<Participant> sortedParticipantsToSettlement = participantsToSettlement
                    .stream()
                    .sorted()
                    .collect(Collectors.toList());
            Participant firstParticipant = sortedParticipantsToSettlement.get(0);
            Participant lastParticipant = sortedParticipantsToSettlement.get(participantsToSettlement.size() - 1);

            double cashToReturn = 0;

            double absFirstBalance = Math.abs(firstParticipant.getBalance());
            double absLastBalance = Math.abs(lastParticipant.getBalance());

            if (absFirstBalance < absLastBalance) {
                cashToReturn = absFirstBalance;
                lastParticipant.setBalance(lastParticipant.getBalance() - absFirstBalance);
                participantsToSettlement.remove(firstParticipant);
            } else if (absFirstBalance > absLastBalance) {
                cashToReturn = absLastBalance;
                firstParticipant.setBalance(firstParticipant.getBalance() + absLastBalance);
                participantsToSettlement.remove(lastParticipant);
            } else {
                cashToReturn = absFirstBalance;
                participantsToSettlement.remove(firstParticipant);
                participantsToSettlement.remove(lastParticipant);
            }

            Settlement settlement = new Settlement(firstParticipant, lastParticipant, cashToReturn);
            settlements.add(settlement);
            settlementRepository.save(settlement);
        }

        participants.forEach(Participant::countBalance);

        return getAll()
                .stream()
                .filter(settlements::contains)
                .collect(Collectors.toList());
    }

    private List<Settlement> getAll() {
        return new ArrayList<>(settlementRepository.findAll());
    }

    public void deletePreviousSettlement(List<Settlement> eventSettlements) {
        eventSettlements.forEach(e -> settlementRepository.deleteById(e.getId()));
    }
}