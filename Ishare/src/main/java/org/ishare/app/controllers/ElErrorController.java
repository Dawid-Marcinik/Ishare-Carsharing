package org.ishare.app.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ElErrorController implements ErrorController{

	 @RequestMapping("/error")
	    public String handleError(ModelMap m) {
	        //do something like logging
		 	m.put("view", "/home/error");
	        return "_t/frame";
	    }
	
	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return null;
	}

}
