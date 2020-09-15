package pl.jparada.app.finalapp.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.jparada.app.finalapp.model.Event;

@Repository
@Qualifier("event")
public interface EventRepository extends CrudRepository<Event, Long> {
}
