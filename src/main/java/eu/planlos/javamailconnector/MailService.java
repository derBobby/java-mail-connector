package eu.planlos.javamailconnector;

import eu.planlos.javamailconnector.config.MailConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MailService implements IMailService {

    private static final String MIME_TYPE = "text/html; charset=utf-8";

    final private MailConfig config;
    final private JavaMailSenderImpl mailSender;

    public MailService(JavaMailSenderImpl mailSender, MailConfig config) {
        this.mailSender = mailSender;
        this.config = config;
    }

    @Override
    public void sendMailTo(String subject, String content, String recipient) {
        sendMailUsingRetry(subject, content, recipient);
    }

    @Override
    public void sendMailToAdmin(String subject, String content) {
        sendMailUsingRetry(subject, content, config.getMailAdmin());
    }

    @Override
    public void sendMailToRecipients(String subject, String content) {
        sendMailUsingRetry(subject, content, config.getMailRecipients());
    }

    private void sendMailUsingRetry(String subject, String content, String recipients) {
        int tryNumber = 0;
        MailException lastException;
        do {
            try {
                sendRetryMail(subject, content, recipients);
                log.debug("Mail has been sent, if enabled.");
                return;
            } catch (MailException e) {
                lastException = e;
                log.error("Sending mail {}/{} was not successful: {}", tryNumber, config.getRetryCount(), e.getMessage());
            }
            tryNumber++;
        } while (tryNumber <= config.getRetryCount());
        throw lastException;
    }

    private void sendRetryMail(String subject, String content, String recipients) {

        if(! config.isActive()) {
            log.info("Mail notifications are disabled. Skip sending");
            return;
        }
        log.info("Mail notifications are enabled. Sending");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipients);
        message.setSubject(addPrefixToSubject(subject));
        message.setText(content);
        message.setFrom(config.getMailAdmin());
        mailSender.send(message);
    }

    private String addPrefixToSubject(String subject) {
        return String.format("%s %s", config.getSubjectPrefix(), subject);
    }
}
