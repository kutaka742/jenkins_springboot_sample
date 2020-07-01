package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
@RestController
public class HelloApplication {
 private final Logger logger = LoggerFactory.getLogger(this.getClass());

 @GetMapping("/")
 String hello(){
  //return "Hello World!";
  //return "This is" + " (" + System.getenv("CF_INSTANCE_INDEX") + ")";
  logger.info("GET hello-app");
  return "Hello App!"; 
}

 public static void main(String[] args) {
  SpringApplication.run(HelloApplication.class, args);
 }
}
