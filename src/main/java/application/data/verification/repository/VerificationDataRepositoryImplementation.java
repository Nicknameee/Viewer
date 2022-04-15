package application.data.verification.repository;

import application.data.users.models.UserActionType;
import application.data.verification.VerificationData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VerificationDataRepositoryImplementation {
    private final Logger logger = LoggerFactory.getLogger(VerificationDataRepositoryImplementation.class);

    private final VerificationDataRepository verificationDataRepository;

    @Autowired
    VerificationDataRepositoryImplementation(VerificationDataRepository verificationDataRepository)
    {
        this.verificationDataRepository = verificationDataRepository;
    }

    public VerificationData saveVerificationData(VerificationData verificationData)
    {
        return verificationDataRepository.save(verificationData);
    }

    public List<VerificationData> getAllVerificationData()
    {
        return verificationDataRepository.findAll();
    }

    public VerificationData getVerificationDataByUUUID(VerificationData verificationData) {
        return verificationDataRepository.getVerificationDataByUUID(verificationData.getCode());
    }

    public VerificationData getVerificationDataByMail(String mail) {
        return verificationDataRepository.getVerificationDataByMail(mail);
    }

    public VerificationData updateVerificationData(String code  ,
                                                   String mail)
    {
        verificationDataRepository.updateVerificationData(code , mail);
        return verificationDataRepository.getVerificationDataByMail(mail);
    }

    public void deleteVerificationData(VerificationData verificationData) {
        verificationDataRepository.deleteVerificationData(verificationData.getCode());
    }

    public void deleteVerificationDataByCode(String code) {
        verificationDataRepository.deleteVerificationData(code);
    }
}