package pl.jparada.app.finalapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jparada.app.finalapp.model.SinglePayment;

@Repository
public interface SinglePaymentRepository extends JpaRepository<SinglePayment, Long> {

    SinglePayment findByPaymentDescriptionAndExpense(String paymentDescription, Double paidAmount);

}
