package application.data.verification.service;

import application.data.users.models.UserActionType;
import application.data.verification.VerificationData;
import application.data.verification.repository.VerificationDataRepository;
import application.data.verification.repository.VerificationDataRepositoryImplementation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VerificationDataService {
    private final Logger logger = LoggerFactory.getLogger(VerificationDataService.class);

    private final VerificationDataRepositoryImplementation verificationDataRepository;

    @Autowired
    VerificationDataService(VerificationDataRepositoryImplementation verificationDataRepository)
    {
        this.verificationDataRepository = verificationDataRepository;
    }

    public VerificationData saveVerificationData(VerificationData verificationData)
    {
        return verificationDataRepository.saveVerificationData(verificationData);
    }

    public List<VerificationData> getAllVerificationData()
    {
        return verificationDataRepository.getAllVerificationData();
    }

    public VerificationData getVerificationDataByUUUID(VerificationData verificationData) {
        return verificationDataRepository.getVerificationDataByUUUID(verificationData);
    }

    public VerificationData getVerificationDataByMail(String mail) {
        return verificationDataRepository.getVerificationDataByMail(mail);
    }

    public VerificationData updateVerificationData(VerificationData verificationData) {
        return verificationDataRepository.updateVerificationData(
                verificationData.getCode() ,
                verificationData.getMail()
        );
    }

    public void deleteVerificationData(VerificationData verificationData) {
        verificationDataRepository.deleteVerificationData(verificationData);
    }

    public void deleteVerificationDataByCode(String code) {
        verificationDataRepository.deleteVerificationDataByCode(code);
    }

    public Boolean checkVerificationDataCoincidence(VerificationData verificationData) {
        VerificationData data = getVerificationDataByUUUID(verificationData);
        if (data != null) {
            return data.getCode().equals(verificationData.getCode());
        }
        return false;
    }
}