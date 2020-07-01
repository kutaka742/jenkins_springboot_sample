package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DemoController {

   	private DemoApplication demoapp;

    @RequestMapping(value="/",method=RequestMethod.GET)
    public ModelAndView index(ModelAndView mav) {
        mav.setViewName("index");
        mav.addObject("msg", "");
        return mav;
    }

    @RequestMapping(value="/",method=RequestMethod.POST)
    public ModelAndView send(@RequestParam("name")String name,ModelAndView mav) {
    	demoapp = new DemoApplication();
        mav.setViewName("index");
        mav.addObject("msg", demoapp.sayhello(name));
        mav.addObject("value",name);
        return mav;
    }
}