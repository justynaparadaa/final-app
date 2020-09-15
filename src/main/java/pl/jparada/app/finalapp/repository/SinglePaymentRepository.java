package pl.jparada.app.finalapp.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.jparada.app.finalapp.model.SinglePayment;

@Repository
@Qualifier("singlePayment")
public interface SinglePaymentRepository extends CrudRepository<SinglePayment, Long> {

    SinglePayment findByPaymentDescriptionAndExpense(String paymentDescription, Double paidAmount);
}
