package com.example.ldpserver;

import com.example.ldpserver.util.FileUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@SpringBootApplication
public class LdpserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(LdpserverApplication.class, args);
        FileUtil.readUserFile();
      //  FileUtil.writeUserFile(null);
    }

    private RestTemplate restTemplate;

    @Bean
    RestTemplate restTemplate() {
        if (restTemplate == null){
            restTemplate = new RestTemplate();
            restTemplate.setErrorHandler(new DefaultResponseErrorHandler(){
                @Override
                public void handleError(ClientHttpResponse response) throws IOException {
                   // super.handleError(response);
                    HttpStatus statusCode = response.getStatusCode();
                    System.out.println("错误码 = "+statusCode);
                }
            });
        }

        return restTemplate;
    }


}
