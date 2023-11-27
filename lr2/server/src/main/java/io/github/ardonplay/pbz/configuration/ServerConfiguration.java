package io.github.ardonplay.pbz.configuration;

import com.sun.net.httpserver.HttpServer;
import io.github.ardonplay.pbz.server.utils.controller.HttpController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.Executors;

@Slf4j
@Configuration
@ComponentScan("io.github.ardonplay.pbz")
public class ServerConfiguration {
    @Value("${server.port}")
    private int serverPort;
    @Value("${server.threads.count}")
    private int threadsCount;
    private final List<HttpController> controllers;

    @Bean
    public HttpServer defaultServer() {
        try {
            log.info("Server port: [{}]", serverPort);
            return HttpServer.create(new InetSocketAddress(serverPort), 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Autowired
    public ServerConfiguration(List<HttpController> controllers) {
        this.controllers = controllers;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        controllers.forEach(controller -> defaultServer().createContext(controller.getPath(), controller.getHandler()));
        defaultServer().setExecutor(Executors.newFixedThreadPool(threadsCount));
        defaultServer().start();
        log.info("Server started");
    }

}
