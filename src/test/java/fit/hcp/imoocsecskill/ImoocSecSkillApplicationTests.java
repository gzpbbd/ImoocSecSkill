package fit.hcp.imoocsecskill;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@SpringBootTest
class ImoocSecSkillApplicationTests {

    @Autowired
    ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        for (String name : applicationContext.getBeanDefinitionNames()) {
            System.out.println("bean name -> " + name);
        }

    }

}
