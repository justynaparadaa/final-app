package pl.jparada.app.finalapp.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.jparada.app.finalapp.model.Participant;

@Repository
@Qualifier("Participant")
public interface ParticipantRepository extends CrudRepository<Participant, Long> {
}
