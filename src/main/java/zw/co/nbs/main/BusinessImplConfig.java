package zw.co.nbs.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import zw.co.nbs.business.Impl.ExcelGenerationServiceImpl;
import zw.co.nbs.business.api.ExcelGenerationService;
import zw.co.nbs.connection.Impl.GatewayConnImpl;
import zw.co.nbs.connection.api.GatewayConn;
import zw.co.nbs.integrations.auth.api.NotificationService;
import zw.co.nbs.integrations.auth.impl.NotificationServiceImpl;

import javax.mail.internet.MimeMessage;
import java.io.InputStream;

@Configuration
public class BusinessImplConfig {

    @Bean
    public GatewayConn gatewayConn(){
        return new GatewayConnImpl();
    }

    @Bean
    public ExcelGenerationService excelGenerationService(final ApplicationContext context){
        return new ExcelGenerationServiceImpl(context);
    }
    @Bean
    public NotificationService notificationService(final ApplicationContext context){
        return new NotificationServiceImpl(context);
    }

    @Bean
    public JavaMailSender mailSender(){return new JavaMailSender() {
            @Override
            public MimeMessage createMimeMessage() {
                return null;
            }

            @Override
            public MimeMessage createMimeMessage(InputStream inputStream) throws MailException {
                return null;
            }

            @Override
            public void send(MimeMessage mimeMessage) throws MailException {

            }

            @Override
            public void send(MimeMessage... mimeMessages) throws MailException {

            }

            @Override
            public void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {

            }

            @Override
            public void send(MimeMessagePreparator... mimeMessagePreparators) throws MailException {

            }

            @Override
            public void send(SimpleMailMessage simpleMailMessage) throws MailException {

            }

            @Override
            public void send(SimpleMailMessage... simpleMailMessages) throws MailException {

            }
        };
    }
}
