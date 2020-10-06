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

        List<Settlement> settlements = new ArrayList<>();

        participants.forEach(Participant::countBalance);

        List<Participant> sortedParticipantsByBalance = participants.stream()
                .sorted()
                .collect(Collectors.toList());

        while (!sortedParticipantsByBalance.isEmpty()) {
            Participant firstParticipant = sortedParticipantsByBalance.get(0);
            Participant lastParticipant = sortedParticipantsByBalance.get(sortedParticipantsByBalance.size() - 1);

            double cashToReturn = 0;

            double absFirstBalance = Math.abs(firstParticipant.getBalance());
            double absLastBalance = Math.abs(lastParticipant.getBalance());

            if (absFirstBalance < absLastBalance) {
                cashToReturn = absFirstBalance;
                lastParticipant.setBalance(lastParticipant.getBalance() - absFirstBalance);
                sortedParticipantsByBalance.remove(firstParticipant);
            } else if (absFirstBalance > absLastBalance){
                cashToReturn = absLastBalance;
                firstParticipant.setBalance(firstParticipant.getBalance() + absLastBalance);
                sortedParticipantsByBalance.remove(lastParticipant);
            } else {
                cashToReturn = absFirstBalance;
                sortedParticipantsByBalance.remove(firstParticipant);
                sortedParticipantsByBalance.remove(lastParticipant);
            }

            Settlement settlement = new Settlement(firstParticipant, lastParticipant, cashToReturn);
            settlementRepository.save(settlement);
        }

        settlementRepository.findAll().forEach(settlements::add);

        return settlements;
    }
}