package com.mjbj.community.provider;

import com.alibaba.fastjson.JSON;
import com.mjbj.community.dto.AccessTokenDTO;
import com.mjbj.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token?client_id="+accessTokenDTO.getClient_id()+"&client_secret="+accessTokenDTO.getClient_secret()
                        +"&code="+accessTokenDTO.getCode() +"&redirect_uri=http://localhost:8887/callback&state=1")
                .build();

            try (Response response = client.newCall(request).execute()) {
                String s = response.body().string();
                String token = s.split("&")[0].split("=")[1];
//                System.out.println(token);
                return token;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
    }

    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .addHeader("Authorization"," token "+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
        }
        return null;
    }
}
