package eu.planlos.javamailconnector;

import eu.planlos.javamailconnector.config.MailConfig;
import eu.planlos.javamailconnector.config.MailSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MailService {

    private static final String MIME_TYPE = "text/html; charset=utf-8";

    final private MailConfig config;
    final private MailSender mailSender;

    public MailService(MailSender mailSender, MailConfig config) {
        this.mailSender = mailSender;
        this.config = config;
    }

    public void sendMailToAdmin(String subject, String content) {
        this.sendMail(subject, content, Recipient.ADMIN);
    }

    public void sendMailToRecipients(String subject, String content) {
        this.sendMail(subject, content, Recipient.RECIPIENTS);
    }

    private void sendMail(String subject, String content, Recipient recipient) {

        if(! config.isActive()) {
            log.info("Mail notifications are disabled. Skip sending");
            return;
        }

        String actualRecipient = config.getMailAdmin();
        if (recipient == Recipient.RECIPIENTS) {
            actualRecipient = config.getMailRecipients();
        }

        log.info("Mail notifications are enabled. Sending");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(actualRecipient);
        message.setSubject(prefixSubject(subject));
        message.setText(content);
        message.setFrom(config.getMailAdmin());
        mailSender.send(message);
    }

    protected void logNotificationOK() {
        log.debug("Notification has been sent, if enabled.");
    }

    protected void logNotificationError(Exception e) {
        log.error("Notification could not been sent: {}", e.getMessage());
        if(log.isDebugEnabled()) {
            e.printStackTrace();
        }
    }

    protected String prefixSubject(String subject) {
        return String.format("%s - %s", prefixTag, subject);
    }

    static enum Recipient {
        ADMIN,
        RECIPIENTS;

        private Recipient() {
        }
    }
}
