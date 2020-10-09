package pl.jparada.app.finalapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jparada.app.finalapp.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

}
