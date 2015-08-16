package org.opencloudengine.serviceportal.controller;

import org.opencloudengine.serviceportal.db.entity.Organization;
import org.opencloudengine.serviceportal.db.entity.User;
import org.opencloudengine.serviceportal.service.MemberService;
import org.opencloudengine.serviceportal.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;

/**
 * Created by swsong on 2015. 8. 16..
 */

@Controller
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:store");
        return mav;
    }

    @RequestMapping(value = "/store", method = RequestMethod.GET)
    public ModelAndView viewStore() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("store");
        return mav;
    }

    @RequestMapping(value = "/api/organization", method = RequestMethod.POST)
    public void getOrganization(@RequestBody(required = false) String json, HttpServletResponse response) throws IOException {
        String json2 = URLDecoder.decode(json);
        Map<String, Object> params = JsonUtil.json2Object(json2);
        String orgId = (String) params.get("orgId");
        Organization organization = memberService.getOrganization(orgId);
        if(organization != null) {
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(JsonUtil.object2String(organization));
        } else {
            response.sendError(404, "no such organization : " + orgId);
            return;
        }
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public ModelAndView signUp() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("signUp");
        return mav;
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public ModelAndView doSignUp(@RequestParam String orgId, @RequestParam String orgName, @RequestParam String userId
            , @RequestParam String password) {


        //1. org 확인.
        ModelAndView mav = new ModelAndView();
        mav.setViewName("signUp");
        return mav;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        session.invalidate();
        mav.setViewName("redirect:/login");
        return mav;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView doLogin(HttpServletRequest request, @RequestParam String userId, @RequestParam String orgId
            , @RequestParam String password, @RequestParam(value="redirect", required=false) String redirect) {
        ModelAndView mav = new ModelAndView();
        User user = new User();
        user.setId(userId);
        user.setOrgId(orgId);
        user.setPassword(password);
        // 로그인 처리 한다.
        if(!memberService.isUserExistsWithPassword(user)) {
            String target = request.getRequestURI();
            String queryString = request.getQueryString();
            if(queryString != null && queryString.length() > 0){
                target += ("?" + queryString);
            }
            mav.setViewName("redirect:" + target);
        } else {
            user = memberService.getUser(userId);
            HttpSession session = request.getSession(true);
            session.setAttribute(User.USER_KEY, user);
            if(redirect != null && redirect.length() > 0){
                mav.setViewName("redirect:"+redirect);
            }else{
                mav.setViewName("redirect:/index");
            }
        }
        return mav;
    }

    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public void doLogin(@RequestBody String json, HttpServletResponse response) throws IOException {
        String json2 = URLDecoder.decode(json);
        User user = JsonUtil.json2Object(json2, User.class);
        logger.debug("user : {}", user);

        String userId = user.getId();

        if(!memberService.isUserExistsWithPassword(user)) {
            response.sendError(404, "incorrect user information : " + userId);
            return;
        }
        user = memberService.getUser(userId);
        response.getWriter().print(JsonUtil.object2String(user));
    }
}
