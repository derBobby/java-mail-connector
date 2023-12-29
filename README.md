# java-mail-connector
This spring boot library allows to send mails.
It adds a retry mechanism and several other possibilities to the `spring-boot-starter-mail`.
See properties below for details.

# Usage

## Maven setup
```xml
        <dependency>
            <groupId>eu.planlos</groupId>
            <artifactId>java-mail-connector</artifactId>
            <version>1.0.0-SNAPSHOT</version>
        </dependency>
```

## Config class setup
```java
@Configuration
@ComponentScan(basePackages = "eu.planlos.javamailconnector")
public class MailPackageConfig {}
```

## Properties setup
| Property                                           | Type    | Default                    | Description                                    |
|----------------------------------------------------|---------|----------------------------|------------------------------------------------|
| `eu.planlos.javamailconnector.active`              | Boolean | false                      | Enable/Disable usage of Mail                   |
| `eu.planlos.javamailconnector.mail-recipients`     | String  | not-configured@example.com | List of default mail recipient mail addresses  |
| `eu.planlos.javamailconnector.mail-admin`          | String  | not-configured@example.com | Admin mail address                             | 
| `eu.planlos.javamailconnector.subject-prefix`      | String  |                            | Prefix to the mail subject                     | 
| `eu.planlos.javamailconnector.retry-count`         | Integer | 1                          | Retry count in case of exception               | 
| `eu.planlos.javamailconnector.retry-interval`      | Integer | 0                          | Retry interval in seconds in case of exception | 
| `spring.mail.properties.mail.transport.protocol`   | String  | smpt                       | See spring docs                                | 
| `spring.mail.properties.mail.smtp.auth`            | String  | true                       | See spring docs                                | 
| `spring.mail.properties.mail.smtp.starttls.enable` | String  | true                       | See spring docs                                | 
| `spring.mail.properties.mail.debug`                | String  | false                      | See spring docs                                | 
| `spring.mail.host`                                 | String  | smtp.example.com           | See spring docs                                | 
| `spring.mail.port`                                 | Integer | 587                        | See spring docs                                | 
| `spring.mail.username`                             | String  | not-configured@example.com | See spring docs                                | 
| `spring.mail.password`                             | String  | not-configured             | See spring docs                                | 

## Use in your code
Autowire the SignalService
```java
class YourClass {
    @Autowired
    private final MailService mailService;
}
```

Call service to use API functions

```java
import org.springframework.mail.MailException;

class YourClass {
    yourMethod() {
        try {
            // Your code
            mailService.sendMailToAdmin("YAY!", "We did it!");
        } catch (MailException e) {
            // your code
        }
    }
}
```
