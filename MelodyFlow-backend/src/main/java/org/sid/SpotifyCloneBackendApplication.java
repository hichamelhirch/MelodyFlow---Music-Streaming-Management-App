package org.sid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@EnableFeignClients
//@ComponentScan(basePackages = {"org.sid.ContextCatalog", "org.sid.ContextUser"})
public class SpotifyCloneBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpotifyCloneBackendApplication.class, args);
    }

}
