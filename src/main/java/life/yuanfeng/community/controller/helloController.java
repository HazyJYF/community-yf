package life.yuanfeng.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class helloController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

}
