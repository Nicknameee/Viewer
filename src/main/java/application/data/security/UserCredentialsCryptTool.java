package application.data.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UserCredentialsCryptTool {
    private PasswordEncoder passwordEncoder;
    private static PasswordEncoder passwordEncoderTool;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initialize() {
        passwordEncoderTool = this.passwordEncoder;
    }
    public static String encodeCredentials(String password) {
        return passwordEncoderTool.encode(password);
    }
}
