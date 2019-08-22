package org.ju.cse.cseju.configuration;

import org.ju.cse.cseju.service.basex.BaseXServerServices;
import org.ju.cse.cseju.service.basex.BaseXServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Kamrul Hasan
 */
@Configuration
public class ConfiguratonBean {

    private static BaseXServerServices baseXServerServices;

    private BaseXServices baseXServices;

    @Bean
    public BaseXServices baseXServices() {
        baseXServerServices = new BaseXServerServices();
        baseXServerServices.startService("syllabus");

        baseXServices = new BaseXServices(baseXServerServices.getSession());
        return baseXServices;
    }
}
