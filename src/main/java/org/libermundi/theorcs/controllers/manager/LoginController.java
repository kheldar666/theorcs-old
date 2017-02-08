package org.libermundi.theorcs.controllers.manager;
import java.util.Calendar;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
 
@Controller
public class LoginController {
    @RequestMapping("/manager/index")
    String index(Model model){
    	model.addAttribute("currentYear", getThisYear());
        return "manager/index";
    }
    
    private int getThisYear() {
    	return Calendar.getInstance().get(Calendar.YEAR);
    }
}