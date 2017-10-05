package org.libermundi.theorcs.controllers;
import java.util.Calendar;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
 
@Controller
public class IndexController implements ErrorController {
	
	private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public String error() {
        return "error";
    }

    @RequestMapping(
    		value = {"/","/index"}
    )
    String index(Model model,WebRequest request){
    	model.addAttribute("currentYear", getThisYear());
        return "index";
    }
    
    private int getThisYear() {
    	return Calendar.getInstance().get(Calendar.YEAR);
    }

	@Override
	public String getErrorPath() {
		return PATH;
	}
}