package zw.co.nbs.main;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import zw.co.nbs.schedule.Impl.CronImpl;
import zw.co.nbs.schedule.api.Cron;

@Configuration
@EnableScheduling
public class ScheduleConfig {

    @Bean
    public Cron cron(final ApplicationContext context){
        return new CronImpl(context);
    }

    @Bean
    public RestTemplateNoSSL restTemplateNoSSL(){
        return new RestTemplateNoSSL();
    }
}
