package life.yuanfeng.community.controller;

/**
 * QuestionService class
 *
 * @author jiyf
 * @create 2019-09-07 17:05
 **/

import life.yuanfeng.community.dto.AccessTokenDTO;
import life.yuanfeng.community.dto.GithubUser;
import life.yuanfeng.community.mapper.UserMapper;
import life.yuanfeng.community.model.User;
import life.yuanfeng.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.UUID;


@Controller
public class AuthorizeController {

    @Autowired
    public GithubProvider githubProvider;

    @Autowired
    public UserMapper userMapper;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.url}")
    private String redirectUrl;


    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code ,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse httpServletResponse) throws UnsupportedEncodingException {

        //创建AccessTokenDTO对象
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_url(redirectUrl);
        accessTokenDTO.setState(state);
        //根据accessTokenDTO获得accessToken
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        //根据accessToken获得User
        GithubUser githubUser = githubProvider.getUser(accessToken);

        if(githubUser != null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setName(githubUser.getName());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(System.currentTimeMillis());
            user.setAvatarUrl(githubUser.getAvatarUrl());
            System.out.println(githubUser.getAvatarUrl());
            userMapper.insert(user);

            httpServletResponse.addCookie(new Cookie("token" , token));

            return "redirect:/";
        }else{
            return "redirect:/";
        }
    }
}
