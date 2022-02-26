package fit.hcp.imoocsecskill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
public class ImoocSecSkillApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ImoocSecSkillApplication.class, args);
        for (String name : run.getBeanDefinitionNames()) {
            System.out.println("bean name ---> " + name);
        }

    }

}
