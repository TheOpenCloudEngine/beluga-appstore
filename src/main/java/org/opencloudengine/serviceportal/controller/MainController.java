package org.opencloudengine.serviceportal.controller;

import org.json.JSONObject;
import org.json.JSONWriter;
import org.opencloudengine.serviceportal.http.ResponseHttpClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.StringWriter;

/**
 * Created by swsong on 2015. 5. 11..
 */
@Controller
public class MainController extends AbstractController {

    @RequestMapping("/index")
    public ModelAndView index(HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:main/cluster.html");
        return mav;
    }

    @RequestMapping("/signUp")
    public ModelAndView signUp() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("signUp");
        return mav;
    }

    @RequestMapping("/login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

    @RequestMapping(value = "/doLogin", method = { RequestMethod.POST })
    public ModelAndView doLogin(HttpSession session, @RequestParam("host") String host, @RequestParam("userId") String userId,
                                @RequestParam("password") String password, @RequestParam(value="redirect", required=false) String redirect) throws Exception {

        logger.debug("login {} : {}:{}", host, userId, password);

        if (host == null || host.length() == 0 || userId.length() == 0 || password.length() == 0) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("redirect:login.html");
            return mav;
        }

        try{
            ResponseHttpClient httpClient = new ResponseHttpClient(host);
			/*
			 * 1. check server is alive
			 * */
            String response = httpClient.httpPost("/ping")
                    .requestText();
            logger.debug("aliveResult > {}", response);
            if(response == null || !response.equals("pong")) {
                //서버 상태 불가.
                ModelAndView mav = new ModelAndView();
                mav.setViewName("redirect:login.html?e=server is not alive");
                return mav;
            }

			/*
			 * 2. proceed login action
			 * */
            JSONObject loginResult = httpClient.httpPost("/login").addParameter("id", userId).addParameter("password", password)
                    .requestJSON();
            logger.debug("loginResult > {}", loginResult);
            if (loginResult != null && loginResult.getInt("status") == 0) {
                // 로그인이 올바를 경우 메인 화면으로 이동한다.

                ModelAndView mav = new ModelAndView();
                if(redirect != null && redirect.length() > 0){
                    mav.setViewName("redirect:"+redirect);
                }else{
                    // 로그인되었다면 바로 start.html로 간다.
                    mav.setViewName("redirect:main/cluster.html");
                }

                String userName = loginResult.getString("name");
                session.setAttribute(USERNAME_ID, userName);
                session.setAttribute(HTTPCLIENT_ID, httpClient);
                return mav;
            }

            ModelAndView mav = new ModelAndView();
            mav.setViewName("login");
            return mav;
        } catch (Throwable t) {
            t.printStackTrace();
            ModelAndView mav = new ModelAndView();
            mav.setViewName("redirect:login.html?e="+t.toString());
            return mav;
        }

    }

    @RequestMapping(value = "/checkAlive", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public String checkAlive(HttpSession session, @RequestParam("host") String host) throws Exception {

        logger.debug("checkAlive {}");

        String message = null;
        ResponseHttpClient httpClient = null;
        try{
            httpClient = new ResponseHttpClient(host, 1);

			/*
			 * 1. check server is alive
			 * */
            String response = httpClient.httpPost("/ping")
                    .requestText();
            logger.debug("aliveResult > {}", response);
            if(response == null || !response.equals("pong")) {
                //서버 상태 불가.
                message = "Server is not alive.";
            }
        }catch(Throwable t){
            message = t.toString();
        } finally {
            httpClient.close();
        }
        StringWriter w = new StringWriter();
        JSONWriter result = new JSONWriter(w);
        result.object();
        result.key("success").value(message == null);
        result.key("message").value(message == null ? "" : message);
        result.endObject();
        return w.toString();
    }

    @RequestMapping("/logout")
    public ModelAndView logout(HttpSession session) throws Exception {

        //세션삭제를 처리한다.
        ResponseHttpClient httpClient = (ResponseHttpClient) session.getAttribute("httpclient");
        if(httpClient != null){
            httpClient.close();
        }
        session.invalidate();
        // 로긴 화면으로 이동한다.
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:login.html");
        return mav;
    }

}
