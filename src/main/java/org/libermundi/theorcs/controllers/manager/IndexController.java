package org.libermundi.theorcs.controllers.manager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
 
@Controller("ManagerIndexController")
public class IndexController {
    @RequestMapping(value = {
    		"/manager/index", "/manager/"
    })
    String index(Model model) throws Exception {
    	if (true) throw new Exception("test");
        return "manager/index";
    }
    
}