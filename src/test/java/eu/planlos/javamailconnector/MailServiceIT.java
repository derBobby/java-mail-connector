package eu.planlos.javamailconnector;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MailServiceIT {

    @Autowired
    private MailService mailService;

    @Test
    public void sendingMail_isSuccessful() {
        mailService.sendMailToAdmin("Integration Test", "Successful?");
    }
}