package life.yuanfeng.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class helloController {

    @GetMapping("/hello")
    public String hello(@RequestParam(name = "name" , defaultValue = "纪源锋") String name , Model model){
         model.addAttribute("name" , name);
         return "hello";
    }
}
