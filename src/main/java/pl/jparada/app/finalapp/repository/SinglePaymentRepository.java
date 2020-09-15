package pl.jparada.app.finalapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.jparada.app.finalapp.model.SinglePayment;

@Repository
public interface SinglePaymentRepository extends CrudRepository<SinglePayment, Long> {

    SinglePayment findByPaymentDescriptionAndExpense(String paymentDescription, Double paidAmount);
}
