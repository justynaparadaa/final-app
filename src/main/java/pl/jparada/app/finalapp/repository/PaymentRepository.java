package pl.jparada.app.finalapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jparada.app.finalapp.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Payment findByPaymentDescriptionAndExpense(String paymentDescription, Double paidAmount);

}
