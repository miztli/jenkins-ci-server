package com.samples.propertiessamples.config;

import com.samples.propertiessamples.model.PropertiesBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment;

/**
 * Springboot's properties priority order
 * source: https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html
 *
 * 1.- Devtools global settings properties on your home directory (~/.spring-boot-devtools.properties when devtools is active).
 * 2.- @TestPropertySource annotations on your tests.
 * 3.- @SpringBootTest#properties annotation attribute on your tests.
 * 4.- Command line arguments.
 * 5.- Properties from SPRING_APPLICATION_JSON (inline JSON embedded in an environment variable or system property).
 * 6.- ServletConfig init parameters.
 * 7.- ServletContext init parameters.
 * 8.- JNDI attributes from java:comp/env.
 * 9.- Java System properties (System.getProperties()).
 * 10.- OS environment variables.
 * 11.- A RandomValuePropertySource that has properties only in random.*.
 * 12.- Profile-specific application properties outside of your packaged jar (application-{profile}.properties and YAML variants).
 * 13.- Profile-specific application properties packaged inside your jar (application-{profile}.properties and YAML variants).
 * 14.- Application properties outside of your packaged jar (application.properties and YAML variants).
 * 15.- Application properties packaged inside your jar (application.properties and YAML variants).
 * 16.- @PropertySource annotations on your @Configuration classes.
 * 17.- Default properties (specified by setting SpringApplication.setDefaultProperties).
 */

@Configuration
public class PropertiesLoading {
    public PropertiesLoading() {
        super();
    }

    /**
     * Injection from spring's Environment
     *      provided in: application.properties by default
     *      override from command line with:
     *      java -jar build/libs/properties-samples-0.0.1-SNAPSHOT.jar --spring.application.name=from_outside
     *
     */
    @Value('${spring.application.name}')
    String appName

    @Autowired
    Environment environment

    @Bean
    PropertiesBean propertiesBean(){
        new PropertiesBean(appName: appName,
                           appNameFromEnvironment: getFromEnv('spring.application.name'),
                           appIp: getFromEnv('server.address'),
                           appPort: getFromEnv('server.port'),
                           appDefaultProfile: environment.defaultProfiles,
                           appProfile: getFromEnv('spring.profiles.active'),
                           userPassword: getFromEnv('security.user.password'),
                           propA: 'prop-a',
                           propB: 'prop-b',
                           propC: 4)
    }

    String getFromEnv(String prop){
        printProperties()
        environment.getProperty(prop)
    }

    void printProperties(){
        println 'size: ' + environment.getProperties().size()
        environment.getProperties().entrySet().each {
                                                      println 'prop: ' + it.key
                                                      println 'value: ' + it.value
                                                    }
    }

}
