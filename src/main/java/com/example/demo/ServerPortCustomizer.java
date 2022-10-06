package com.example.demo;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;

@Component
public class ServerPortCustomizer 
  implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
 
    private static final Logger logger = LoggerFactory.getLogger(ServerPortCustomizer.class);


    @Value("${service.port}")
    private Integer service_port; 

    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
        logger.info(String.format("service_port: %s", service_port));
        factory.setPort(service_port);
    }
}
