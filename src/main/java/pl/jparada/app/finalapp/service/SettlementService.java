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

    public List<Settlement> makeSettlement(List<Participant> participants) {

        ArrayList<Settlement> settlements = new ArrayList<>();

        participants.forEach(Participant::countBalance);

        List<Participant> sortedParticipantsByBalance = participants.stream()
                .sorted()
                .collect(Collectors.toList());

        while (!sortedParticipantsByBalance.isEmpty()) {
            Participant firstParticipant = sortedParticipantsByBalance.get(0);
            Participant lastParticipant = sortedParticipantsByBalance.get(sortedParticipantsByBalance.size() - 1);

            double cash = 0;

            double absFirstBalance = Math.abs(firstParticipant.getBalance());
            double absLastBalance = Math.abs(lastParticipant.getBalance());

            if (absFirstBalance < absLastBalance) {
                cash = absFirstBalance;
                lastParticipant.setBalance(lastParticipant.getBalance() - absFirstBalance);
                sortedParticipantsByBalance.remove(firstParticipant);
            } else if (absFirstBalance > absLastBalance){
                cash = absLastBalance;
                firstParticipant.setBalance(firstParticipant.getBalance() + absLastBalance);
                sortedParticipantsByBalance.remove(lastParticipant);
            } else {
                cash = absFirstBalance;
                sortedParticipantsByBalance.remove(firstParticipant);
                sortedParticipantsByBalance.remove(lastParticipant);
            }

            Settlement settlement = new Settlement(firstParticipant, lastParticipant, cash);
            settlementRepository.save(settlement);
        }

        settlementRepository.findAll().forEach(settlements::add);

        return settlements;
    }
}