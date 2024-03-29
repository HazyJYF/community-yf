package life.yuanfeng.community.provider;

import com.alibaba.fastjson.JSON;
import life.yuanfeng.community.dto.AccessTokenDTO;
import life.yuanfeng.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * QuestionService class
 *
 * @author jiyf
 * @create 2019-09-07 17:05
 **/

@Component
public class GithubProvider {

    //根据返回的code,state和其他相关信息发送http请求得到token
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //根据返回的token得到user
    public GithubUser getUser(String accessToken){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try {
             Response response = okHttpClient.newCall(request).execute();
             String string = response.body().string();
             GithubUser githubUser = JSON.parseObject(string , GithubUser.class);
             return githubUser;
        } catch (IOException e) {
        }
        return null;
    }
}
