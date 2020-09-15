package pl.jparada.app.finalapp.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.jparada.app.finalapp.model.PaymentParticipants;

@Repository
@Qualifier("PaymentParticipant")
public interface PaymentParticipantsRepository extends CrudRepository<PaymentParticipants, Long> {
}
