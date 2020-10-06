package pl.jparada.app.finalapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.jparada.app.finalapp.model.Settlement;

@Repository
public interface SettlementRepository extends CrudRepository<Settlement, Long> {
}
