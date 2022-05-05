package application.data.payment.service;

import application.data.payment.PaymentModel;
import application.data.payment.repository.PaymentRepositoryImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    private PaymentRepositoryImplementation paymentRepository;

    @Autowired
    public void setPaymentRepository(PaymentRepositoryImplementation paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<PaymentModel> getAll() {
        return paymentRepository.getAll();
    }

    public PaymentModel getPaymentModelById(Long id) {
        return paymentRepository.getPaymentModelById(id);
    }

    public PaymentModel savePaymentModel(PaymentModel paymentModel) {
        return paymentRepository.savePaymentModel(paymentModel);
    }

    public void updatePaymentModel(String card , String iban , String receiver , Long id) {
        paymentRepository.updatePaymentModel(card , iban , receiver , id);
    }

    public void deletePaymentModelById(Long id) {
        paymentRepository.deletePaymentModelById(id);
    }
}
