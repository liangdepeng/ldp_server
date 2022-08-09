package com.example.ldpserver;

import com.example.ldpserver.util.FileUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class LdpserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(LdpserverApplication.class, args);
      //  FileUtil.readUserFile();
      //  FileUtil.writeUserFile(null);
    }

    private RestTemplate restTemplate;

    @Bean
    RestTemplate restTemplate() {
        if (restTemplate == null)
            restTemplate = new RestTemplate();
        return restTemplate;
    }


}
