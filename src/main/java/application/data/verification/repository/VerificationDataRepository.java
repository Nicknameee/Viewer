package application.data.verification.repository;

import application.data.verification.VerificationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface VerificationDataRepository extends JpaRepository<VerificationData, Long> {
    @Query("SELECT v FROM VerificationData v WHERE v.code=:code")
    VerificationData getVerificationDataByUUID(@Param("code") String code);

    @Query("SELECT v FROM VerificationData v WHERE v.mail=:mail")
    VerificationData getVerificationDataByMail(@Param("mail") String mail);

    @Modifying
    @Transactional
    @Query("UPDATE VerificationData v SET v.code=:code WHERE v.mail=:mail")
    void updateVerificationData(@Param("code") String code  ,
                                @Param("mail") String mail);

    @Modifying
    @Transactional
    @Query("DELETE FROM VerificationData v WHERE v.code=:code")
    void deleteVerificationData(@Param("code") String code);
}