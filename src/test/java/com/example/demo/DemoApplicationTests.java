package com.example.demo;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    private DemoApplication demoapp;

    @Before
    public void setUp() {
    	demoapp = new DemoApplication();
    }

    @Test
    public void greeting_post() {
        assertThat(demoapp.sayhello(null), is("言語を選択してください。"));
    }

    @Test
    public void greeting_english() {
        assertThat(demoapp.sayhello("English"), is("hello"));
    }

    @Test
    public void greeting_japanease() {
        assertThat(demoapp.sayhello("Japanease"), is("こんにちは"));
    }

    @Test
    public void greeting_chinease() {
        assertThat(demoapp.sayhello("Chinease"), is("您好"));
    }

    @Test
    public void greeting_etc() {
        assertThat(demoapp.sayhello("ETC"), is("未設定"));
    }

}