package com.pragma.integracion.protocolos.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.client.WebClientGraphQLClient;
import org.springframework.graphql.client.reactive.ReactorNettyWebSocketGraphQLClient;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class ProtocolConfig {

    @Bean
    public WebClient httpClient() {
        ConnectionProvider provider = ConnectionProvider.builder("protocol-http-pool")
            .maxConnections(500)
            .maxIdleTime(Duration.ofSeconds(30))
            .maxLifeTime(Duration.ofMinutes(5))
            .pendingAcquireTimeout(Duration.ofSeconds(30))
            .build();

        HttpClient httpClient = HttpClient.create(provider)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
            .responseTimeout(Duration.ofSeconds(30))
            .doOnConnected(conn -> conn
                .addHandlerLast(new ReadTimeoutHandler(30, TimeUnit.SECONDS))
                .addHandlerLast(new WriteTimeoutHandler(30, TimeUnit.SECONDS)));

        return WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .defaultHeader("Content-Type", "application/json")
            .defaultHeader("Accept", "application/json")
            .build();
    }

    @Bean
    public WebClient grpcWebClient() {
        ConnectionProvider provider = ConnectionProvider.builder("grpc-http-pool")
            .maxConnections(200)
            .maxIdleTime(Duration.ofSeconds(60))
            .maxLifeTime(Duration.ofMinutes(10))
            .pendingAcquireTimeout(Duration.ofSeconds(45))
            .build();

        HttpClient httpClient = HttpClient.create(provider)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 15000)
            .responseTimeout(Duration.ofSeconds(60));

        return WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .defaultHeader("Content-Type", "application/grpc")
            .defaultHeader("Accept", "application/grpc+proto")
            .build();
    }

    @Bean
    public WebClient amqpManagementClient() {
        ConnectionProvider provider = ConnectionProvider.builder("amqp-mgmt-pool")
            .maxConnections(50)
            .maxIdleTime(Duration.ofSeconds(20))
            .maxLifeTime(Duration.ofMinutes(3))
            .pendingAcquireTimeout(Duration.ofSeconds(20))
            .build();

        HttpClient httpClient = HttpClient.create(provider)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .responseTimeout(Duration.ofSeconds(15));

        return WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .defaultHeader("Content-Type", "application/json")
            .build();
    }

    @Bean
    public WebClientCustomizer graphqlWebClientCustomizer() {
        return webClientBuilder -> {
            WebClient graphqlWebClient = webClientBuilder
                .baseUrl("http://localhost:8080/graphql")
                .defaultHeader("Content-Type", "application/json")
                .build();
        };
    }

    @Bean
    public ConnectionProvider rsocketConnectionProvider() {
        return ConnectionProvider.builder("rsocket-pool")
            .maxConnections(100)
            .maxIdleTime(Duration.ofSeconds(45))
            .maxLifeTime(Duration.ofMinutes(8))
            .pendingAcquireTimeout(Duration.ofSeconds(25))
            .build();
    }

    @Bean
    public ProtocolProperties protocolProperties() {
        return new ProtocolProperties();
    }

    public static class ProtocolProperties {
        private int grpcPort = 9090;
        private int rsocketPort = 7000;
        private String amqpHost = "localhost";
        private int amqpPort = 5672;
        private String amqpUsername = "guest";
        private String amqpPassword = "guest";
        private int connectionTimeout = 10000;
        private int requestTimeout = 30000;
        private int maxRetries = 3;
        private long retryDelayMs = 1000;

        public int getGrpcPort() {
            return grpcPort;
        }

        public void setGrpcPort(int grpcPort) {
            this.grpcPort = grpcPort;
        }

        public int getRsocketPort() {
            return rsocketPort;
        }

        public void setRsocketPort(int rsocketPort) {
            this.rsocketPort = rsocketPort;
        }

        public String getAmqpHost() {
            return amqpHost;
        }

        public void setAmqpHost(String amqpHost) {
            this.amqpHost = amqpHost;
        }

        public int getAmqpPort() {
            return amqpPort;
        }

        public void setAmqpPort(int amqpPort) {
            this.amqpPort = amqpPort;
        }

        public String getAmqpUsername() {
            return amqpUsername;
        }

        public void setAmqpUsername(String amqpUsername) {
            this.amqpUsername = amqpUsername;
        }

        public String getAmqpPassword() {
            return amqpPassword;
        }

        public void setAmqpPassword(String amqpPassword) {
            this.amqpPassword = amqpPassword;
        }

        public int getConnectionTimeout() {
            return connectionTimeout;
        }

        public void setConnectionTimeout(int connectionTimeout) {
            this.connectionTimeout = connectionTimeout;
        }

        public int getRequestTimeout() {
            return requestTimeout;
        }

        public void setRequestTimeout(int requestTimeout) {
            this.requestTimeout = requestTimeout;
        }

        public int getMaxRetries() {
            return maxRetries;
        }

        public void setMaxRetries(int maxRetries) {
            this.maxRetries = maxRetries;
        }

        public long getRetryDelayMs() {
            return retryDelayMs;
        }

        public void setRetryDelayMs(long retryDelayMs) {
            this.retryDelayMs = retryDelayMs;
        }
    }
}