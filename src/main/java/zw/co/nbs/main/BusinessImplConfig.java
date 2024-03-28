package zw.co.nbs.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zw.co.nbs.business.Impl.ExcelGenerationServiceImpl;
import zw.co.nbs.business.api.ExcelGenerationService;
import zw.co.nbs.connection.Impl.GatewayConnImpl;
import zw.co.nbs.connection.api.GatewayConn;

@Configuration
public class BusinessImplConfig {

    @Bean
    public GatewayConn gatewayConn(){
        return new GatewayConnImpl();
    }

    @Bean
    public ExcelGenerationService excelGenerationService(final ApplicationContext context){
        return new ExcelGenerationServiceImpl();
    }
}
