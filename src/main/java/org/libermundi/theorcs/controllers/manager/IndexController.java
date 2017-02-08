package org.libermundi.theorcs.controllers.manager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
 
@Controller("ManagerIndexController")
public class IndexController {
    @RequestMapping("/manager/login")
    String index(Model model){
        return "manager/login";
    }
    
}