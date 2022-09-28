package com.xq.hdb.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 由于工作台要求需返回 HTTP/1.1 200 OK
 * 开启sendReasonPhrase，springboot 2.1.5 需配合tomcat 8.5
 * 开启后 HTTP/1.1 200 ->HTTP/1.1 200 OK
 */
@Configuration
public class TomcatConfig {

    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {

        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();

        factory.addConnectorCustomizers(
                //由于工作台要求需返回 HTTP/1.1 200 OK
                //开启sendReasonPhrase，springboot 2.1.5 需配合tomcat 8.5
                //开启后 HTTP/1.1 200 ->HTTP/1.1 200 OK
                connector -> connector.setAttribute("sendReasonPhrase", "true"));
        return factory;
    }
}

