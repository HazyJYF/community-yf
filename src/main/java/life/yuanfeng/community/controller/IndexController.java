package life.yuanfeng.community.controller;

import life.yuanfeng.community.dto.QuestionDTO;
import life.yuanfeng.community.mapper.UserMapper;
import life.yuanfeng.community.model.User;
import life.yuanfeng.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;


    @GetMapping("/")
    public String index(HttpServletRequest httpServletRequest ,
                        Model model) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if(cookies != null && cookies.length != 0){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        httpServletRequest.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        List<QuestionDTO> questionDTOS = questionService.list();
        model.addAttribute("questions" , questionDTOS);
        return "index";
    }

    @GetMapping("/signDown")
    public String signDown(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse) {

        Cookie[] cookies = httpServletRequest.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                cookie.setMaxAge(0);
                httpServletResponse.addCookie(cookie);
                break;
            }
        }
        httpServletRequest.getSession().removeAttribute("user");
        return "index";
    }
}
