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

    public VerificationData getVerificationDataByMailAndActionType(VerificationData verificationData) {
        return verificationDataRepository.getVerificationDataByUUID(verificationData.getCode());
    }

    public void deleteVerificationData(VerificationData verificationData) {
        verificationDataRepository.deleteVerificationData(verificationData.getCode());
    }
}