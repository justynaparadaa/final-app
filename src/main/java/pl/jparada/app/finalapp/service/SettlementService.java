package pl.jparada.app.finalapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jparada.app.finalapp.model.Participant;
import pl.jparada.app.finalapp.model.Settlement;
import pl.jparada.app.finalapp.repository.SettlementRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class SettlementService {

    @Autowired
    private SettlementRepository settlementRepository;

    public List<Settlement> makeAndSaveSettlement(List<Participant> participants) {

        Logger logger = Logger.getLogger("InfoLogger");
        List<Settlement> settlements = new ArrayList<>();

        try {
            participants.forEach(Participant::countBalance);
            List<Participant> participantsToSettlement = new ArrayList<>(participants);
            List<Participant> filterParticipantsToSettlement = participantsToSettlement.stream()
                    .filter(e -> e.getBalance() != 0)
                    .collect(Collectors.toList());

            Comparator<Participant> byBalance = Comparator.comparing(Participant::getBalance);

            while (!filterParticipantsToSettlement.isEmpty()) {
                participantsToSettlement.sort(byBalance);
                Participant firstParticipant = filterParticipantsToSettlement.get(0);
                Participant lastParticipant = filterParticipantsToSettlement.get(filterParticipantsToSettlement.size() - 1);

                double cashToReturn;

                double absFirstBalance = Math.abs(firstParticipant.getBalance());
                double absLastBalance = Math.abs(lastParticipant.getBalance());

                if (absFirstBalance < absLastBalance) {
                    cashToReturn = absFirstBalance;
                    lastParticipant.setBalance(lastParticipant.getBalance() - absFirstBalance);
                    filterParticipantsToSettlement.remove(firstParticipant);
                } else if (absFirstBalance > absLastBalance) {
                    cashToReturn = absLastBalance;
                    firstParticipant.setBalance(firstParticipant.getBalance() + absLastBalance);
                    filterParticipantsToSettlement.remove(lastParticipant);
                } else {
                    cashToReturn = absFirstBalance;
                    filterParticipantsToSettlement.remove(firstParticipant);
                    filterParticipantsToSettlement.remove(lastParticipant);
                }

                Settlement settlement = new Settlement(firstParticipant, lastParticipant, cashToReturn);
                settlements.add(settlement);
                settlementRepository.save(settlement);
            }

            participants.forEach(Participant::countBalance);

        } catch (NullPointerException e){
            logger.info("Participants list is null");
        }

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