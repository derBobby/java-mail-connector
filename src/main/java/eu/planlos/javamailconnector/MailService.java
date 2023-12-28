package eu.planlos.javamailconnector;

import eu.planlos.javamailconnector.config.MailConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MailService {

    private static final String MIME_TYPE = "text/html; charset=utf-8";

    final private MailConfig config;
    final private JavaMailSenderImpl mailSender;

    public MailService(JavaMailSenderImpl mailSender, MailConfig config) {
        this.mailSender = mailSender;
        this.config = config;
    }

    public void sendMailTo(String subject, String content, String recipient) {
        sendMailUsingRetry(subject, content, recipient);
    }

    public void sendMailToAdmin(String subject, String content) {
        sendMailUsingRetry(subject, content, config.getMailAdmin());
    }

    public void sendMailToRecipients(String subject, String content) {
        sendMailUsingRetry(subject, content, config.getMailRecipients());
    }

    private void sendMailUsingRetry(String subject, String content, String recipients) {

        for(int tryNumber=0; tryNumber<=config.getRetryCount(); tryNumber++) {

            try {
                sendRetryMail(subject, content, recipients);
                logMailOK();
                return;
            } catch (Exception e) {
                logRetryMailError(tryNumber, e);
            }
        }
    }

    private void sendRetryMail(String subject, String content, String recipients) {

        if(! config.isActive()) {
            log.info("Mail notifications are disabled. Skip sending");
            return;
        }

        log.info("Mail notifications are enabled. Sending");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipients);
        message.setSubject(prefixSubject(subject));
        message.setText(content);
        message.setFrom(config.getMailAdmin());
        mailSender.send(message);
    }

    private void logMailOK() {
        log.debug("Mail has been sent, if enabled.");
    }

    private void logRetryMailError(int tryNumber, Exception e) {
        log.error("Sending mail {}/{} was not successful: {}", tryNumber, config.getRetryCount(), e.getMessage());
    }

    private String prefixSubject(String subject) {
        return String.format("%s %s", config.getSubjectPrefix(), subject);
    }
}
