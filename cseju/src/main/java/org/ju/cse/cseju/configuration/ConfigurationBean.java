package org.ju.cse.cseju.configuration;

import org.ju.cse.cseju.repository.basex.BaseXRepository;
import org.ju.cse.cseju.service.basex.BaseXServerServices;
import org.ju.cse.cseju.service.basex.BaseXServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Kamrul Hasan
 */
@Configuration
public class ConfigurationBean {

    private static final String DATABASE_NAME = "syllabus";

    private static BaseXServerServices baseXServerServices;
    private BaseXRepository baseXRepository;

    @Bean
    public BaseXRepository baseXRepository() {
        baseXServerServices = new BaseXServerServices();
        baseXServerServices.startService(DATABASE_NAME);

        this.baseXRepository = new BaseXRepository(
                baseXServerServices.getSession()
        );
        return this.baseXRepository;
    }
}
