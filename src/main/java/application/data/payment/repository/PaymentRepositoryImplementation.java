package application.data.payment.repository;

import application.data.payment.PaymentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PaymentRepositoryImplementation {
    private PaymentRepository paymentRepository;

    @Autowired
    public void setPaymentRepository(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<PaymentModel> getAll() {
        return paymentRepository.findAll();
    }

    public PaymentModel getPaymentModelById(Long id) {
        return paymentRepository.getPaymentModelById(id);
    }

    public PaymentModel savePaymentModel(PaymentModel paymentModel) {
        return paymentRepository.save(paymentModel);
    }

    public void updatePaymentModel(String card , String iban , String receiver , Long id) {
        paymentRepository.updatePaymentModel(card , iban , receiver , id);
    }

    public void deletePaymentModelById(Long id) {
        paymentRepository.deletePaymentModelById(id);
    }
}