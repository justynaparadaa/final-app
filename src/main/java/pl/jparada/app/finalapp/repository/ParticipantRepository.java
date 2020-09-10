package pl.jparada.app.finalapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.jparada.app.finalapp.model.Participant;

@Repository
public interface ParticipantRepository extends CrudRepository<Participant, Long> {
}
