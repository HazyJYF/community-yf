package life.yuanfeng.community.controller;

import life.yuanfeng.community.mapper.QuestionMapper;
import life.yuanfeng.community.mapper.UserMapper;
import life.yuanfeng.community.model.Question;
import life.yuanfeng.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PushlishController {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping("/pushlish")
    public String pushlish(){
        return "pushlish";
    }

    @PostMapping("/pushlish")
    public String doPushlish(
            @RequestParam(value = "title" , required = false) String title,
            @RequestParam(value = "description" , required = false) String description,
            @RequestParam(value = "tag" , required = false) String tag,
            HttpServletRequest httpServletRequest,
            Model model
    ){

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        if(title == null || title.equals("")){
            model.addAttribute("error" , "问题描述不能为空");
            return "pushlish";
        }

        if(description == null || description.equals("")){
            model.addAttribute("error" , "问题补充不能为空");
            return "pushlish";
        }

        if(tag == null || tag.equals("")){
            model.addAttribute("error" , "标签不能为空");
            return "pushlish";
        }

        //获取登录用户信息
        User user = null;
        Cookie[] cookies = httpServletRequest.getCookies();
        if(cookies != null && cookies.length != 0){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
                    if (user != null) {
                        httpServletRequest.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        if (user == null){
            model.addAttribute("error","用户未登录");
            return "pushlish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(System.currentTimeMillis());
        question.setCreator(user.getId());

        questionMapper.createQuestion(question);
        return "redirect:/";

    }
}

