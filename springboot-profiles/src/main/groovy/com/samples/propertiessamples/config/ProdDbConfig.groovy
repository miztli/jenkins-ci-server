package com.samples.propertiessamples.config

import com.samples.propertiessamples.model.DbPropertiesBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.env.Environment

@Configuration
@Profile(Profiles.prod)
class ProdDbConfig {

    @Autowired
    Environment environment

    @Bean
    DbPropertiesBean dbPropertiesBean(){
        new DbPropertiesBean(
                dbProfile: Profiles.prod,
                dbName: getFromEnv('db.name'),
                dbAddress: getFromEnv('db.address'),
                dbPort: getFromEnv('db.port'),
                dbPassword: getFromEnv('db.password'))
    }

    String getFromEnv(String prop){
        environment.getProperty(prop)
    }
}

