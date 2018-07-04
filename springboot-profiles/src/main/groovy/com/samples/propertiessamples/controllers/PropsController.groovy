package com.samples.propertiessamples.controllers

import com.samples.propertiessamples.model.DbPropertiesBean
import com.samples.propertiessamples.model.PropertiesBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PropsController {

    @Autowired
    PropertiesBean propertiesBean

    @Autowired
    DbPropertiesBean dbPropertiesBean

    @GetMapping("/app-props")
    public PropertiesBean getAppProps(){
        propertiesBean
    }

    @GetMapping("/db-props")
    public DbPropertiesBean getDbProps(){
        dbPropertiesBean
    }

}
