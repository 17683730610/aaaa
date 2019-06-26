package com.example.service.impl;

import com.example.dao.JobDao;
import com.example.dao.TestDao;
import com.example.pojo.Job;
import com.example.pojo.User;
import com.example.service.TestService;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.List;
import java.util.UUID;

/**
 * @author lijinlong
 * @version 1.0
 * @date 2019/6/12 14:11
 */

@Service
public class TestServiceImpl implements TestService {
    private static final Logger log = LoggerFactory.getLogger(TestServiceImpl.class);

    @Resource
    private TestDao testDao;
    @Resource
    private JobDao jobDao;


    @Override
    public String userLogin(HttpServletRequest request) {
        String refererUrl = request.getHeader("referer");
        log.info("请求的跳转路径:" + refererUrl);
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User identifyUsername = testDao.findUserByName(username);
        User identifyPassword = testDao.findUserByPassword(password);

        if (identifyPassword != null) {
            if (identifyUsername != null) {
                log.info("授权成功");
                try {
                    String encodeUrl = URLEncoder.encode(refererUrl, "GBK");
                    String loginUrl = "http://192.168.5.206:8081/login.html";
                    int loginUrlLength = loginUrl.length();
                    String outUrl = encodeUrl.substring(loginUrlLength, encodeUrl.length());
                    String codeUrl = "http://192.168.5.206:8081/test/getCode?";

                    return codeUrl + outUrl;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                log.info("用户名错误");
            }
        } else {
            log.info("密码错误");
        }
        return null;
    }

    @Override
    public List<Job> findJob(String name) {
        return jobDao.findJobsByName(name);
    }

    @Override
    public String getCode(HttpServletRequest request) {
        try {
            OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);
            String clientId = oauthRequest.getClientId();
            String clientSecret = oauthRequest.getClientSecret();
            String redirectURI = oauthRequest.getRedirectURI();
            String responseType = oauthRequest.getResponseType();
            log.info("请求" + clientId + clientSecret + redirectURI + responseType);

            String token = oauthRequest.getParam("token");

            if (clientId != null && !"".equals(clientId)) {
                String code = UUID.randomUUID().toString().substring(0, 20);
                OAuthASResponse.OAuthAuthorizationResponseBuilder builder = OAuthASResponse.authorizationResponse(request, HttpServletResponse.SC_FOUND);
                builder.setParam("token", token);
                builder.setParam("state", "11");
                builder.setCode(code);
                String redirectUri = oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI);
                final OAuthResponse response = builder.location(redirectUri).buildQueryMessage();
                log.info("回调路径" + response.getLocationUri());

                HttpHeaders httpHeaders = new HttpHeaders();
                try {
                    httpHeaders.setLocation(new URI(response.getLocationUri()));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                String decodeUrl = URLDecoder.decode(redirectUri,"UTF-8");
                String strURL = "http://www.192.168.5.206:8081/test/accessToken?grant_type=authorization_code&client_id=" + clientId + "&client_secret=" + clientSecret + "&redirect_uri=" + decodeUrl + "&code=" + code;

                try {
                    URL url = new URL(strURL);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setUseCaches(false);
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Accept", "application/json");
                    connection.setRequestProperty("Content-Type", "application/json");

                    return redirectUri;
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (OAuthSystemException | OAuthProblemException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public HttpEntity accessToken(HttpServletRequest request) {
        try {
            OAuthTokenRequest tokenRequest = new OAuthTokenRequest(request);
            String code = tokenRequest.getParam(OAuth.OAUTH_CODE);
            if (code != null && !"".equals(code)) {
                OAuthIssuerImpl authIssuer = new OAuthIssuerImpl(new MD5Generator());
                final String accessToken = authIssuer.accessToken();
                final String refreshToken = authIssuer.refreshToken();

                OAuthResponse authResponse = OAuthASResponse
                        .tokenResponse(HttpServletResponse.SC_OK)
                        .setAccessToken(accessToken)
                        .setRefreshToken(refreshToken)
                        .setParam("expires_in", "7200000")
                        .buildJSONMessage();
                 log.info("请求体"+authResponse.getBody());


            }
        } catch (OAuthSystemException | OAuthProblemException e) {
            e.printStackTrace();
        }
        return null;
    }

}
