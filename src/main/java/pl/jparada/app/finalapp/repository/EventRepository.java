package pl.jparada.app.finalapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.jparada.app.finalapp.model.Event;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
}
