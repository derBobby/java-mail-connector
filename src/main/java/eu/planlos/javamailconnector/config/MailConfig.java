package eu.planlos.javamailconnector.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

	@Getter	private final boolean active;
	@Getter	private final String mailRecipients;
	@Getter	private final String mailAdmin;
	@Getter	private final String subjectPrefix;
	@Getter	private final Integer retryCount;
	@Getter private final Integer retryInterval;

	private final String mailTransportProtocol;
	private final String mailSmtpAuth;
	private final String mailSmtpStarttlsEnable;
	private final String mailDebug;
	private final String mailHost;
	private final int mailPort;
	private final String mailUsername;
	private final String mailPassword;

	public MailConfig(
			@Value("${eu.planlos.javamailconnector.active:false}") boolean active,
			@Value("${eu.planlos.javamailconnector.mail-recipients:not-configured@example.com}") String mailRecipients,
			@Value("${eu.planlos.javamailconnector.mail-admin:not-configured@example.com}") String mailAdmin,
			@Value("${eu.planlos.javamailconnector.subject-prefix:}") String subjectPrefix,
			@Value("${eu.planlos.javamailconnector.retry-count:1}") Integer retryCount,
			@Value("${eu.planlos.javamailconnector.retry-interval:0}") Integer retryInterval,

			@Value("${spring.mail.properties.mail.transport.protocol:smtp}") String mailTransportProtocol,
			@Value("${spring.mail.properties.mail.smtp.auth:true}") String mailSmtpAuth,
			@Value("${spring.mail.properties.mail.smtp.starttls.enable:true}") String mailSmtpStarttlsEnable,
			@Value("${spring.mail.properties.mail.debug:false}") String mailDebug,
			@Value("${spring.mail.host:smtp.example.com}") String mailHost,
			@Value("${spring.mail.port:587}") int mailPort,
			@Value("${spring.mail.username:not-configured@example.com}") String mailUsername,
			@Value("${spring.mail.password:not-configured}") String mailPassword
	) {
		this.active = active;
		this.mailRecipients = mailRecipients;
		this.mailAdmin = mailAdmin;
		this.subjectPrefix = subjectPrefix;
		this.retryCount = retryCount;
		this.retryInterval = retryInterval;
		this.mailTransportProtocol = mailTransportProtocol;
		this.mailSmtpAuth = mailSmtpAuth;
		this.mailSmtpStarttlsEnable = mailSmtpStarttlsEnable;
		this.mailDebug = mailDebug;
		this.mailHost = mailHost;
		this.mailPort = mailPort;
		this.mailUsername = mailUsername;
		this.mailPassword = mailPassword;
	}

	@Bean
	public JavaMailSenderImpl mailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(mailHost);
		mailSender.setPort(mailPort);
		mailSender.setUsername(mailUsername);
		mailSender.setPassword(mailPassword);
		mailSender.setJavaMailProperties(properties());
		return mailSender;
	}

	private Properties properties() {
		Properties props = new Properties();
		props.put("mail.transport.protocol", mailTransportProtocol);
		props.put("mail.smtp.auth", mailSmtpAuth);
		props.put("mail.smtp.starttls.enable", mailSmtpStarttlsEnable);
		props.put("mail.debug", mailDebug);
		return props;
	}
}