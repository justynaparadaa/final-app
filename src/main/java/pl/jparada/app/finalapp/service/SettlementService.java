package pl.jparada.app.finalapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jparada.app.finalapp.model.Participant;
import pl.jparada.app.finalapp.model.Settlement;
import pl.jparada.app.finalapp.repository.SettlementRepository;

import java.util.List;

@Service
public class SettlementService {

    @Autowired
    private SettlementRepository settlementRepository;

    public List<Settlement> makeSettlement(List<Participant> participants) {
        
    }
}
