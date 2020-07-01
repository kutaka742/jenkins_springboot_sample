package com.example.demo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public String sayhello(String hello) {

    	String str = null;

    	if(StringUtils.equals(hello,"English")){
    		str = "hello";
    	}else if (StringUtils.equals(hello,"Japanease")){
    		str = "こんにちは";
    	}else if (StringUtils.equals(hello,"Chinease")){
    		str = "您好";
    	}else if (StringUtils.equals(hello,"ETC")) {
    		str = "未設定";
    	} else {
    		str = "言語を選択してください。";
    	}

    	return str;
    }

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
