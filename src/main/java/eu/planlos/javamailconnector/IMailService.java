package eu.planlos.javamailconnector;

public interface IMailService {
    void sendMailTo(String subject, String content, String recipient);

    void sendMailToAdmin(String subject, String content);

    void sendMailToRecipients(String subject, String content);
}
