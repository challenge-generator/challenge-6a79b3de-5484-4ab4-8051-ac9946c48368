package com.pragma.integracion.protocolos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import reactor.core.publisher.Hooks;

@SpringBootApplication
@EnableAsync
public class ProtocolosApplication {

    public static void main(String[] args) {
        Hooks.onOperatorDebug();
        SpringApplication.run(ProtocolosApplication.class, args);
    }

    @Bean
    public String applicationInfo(Environment env) {
        String appName = env.getProperty("spring.application.name", "integracion-protocolos");
        String version = env.getProperty("build.version", "0.0.1-SNAPSHOT");
        System.out.printf("Starting %s v%s%n", appName, version);
        return appName;
    }
}