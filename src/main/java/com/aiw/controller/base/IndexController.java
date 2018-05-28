package com.aiw.controller.base;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
@Scope("prototype")
public class IndexController {
   
	
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(){
		 ModelAndView modelAndView = new ModelAndView();  
	     modelAndView.setViewName("/index");  
	     return modelAndView;  
    }
	
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView index2(){
		 ModelAndView modelAndView = new ModelAndView();  
	     modelAndView.setViewName("/index");  
	     return modelAndView;  
    }

	
}
	
