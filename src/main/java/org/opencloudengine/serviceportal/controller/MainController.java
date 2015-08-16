package org.opencloudengine.serviceportal.controller;

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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        mav.setViewName("redirect:store.html");
        return mav;
    }

    @RequestMapping(value = "/signUp")
    public ModelAndView signUp() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("signUp");
        return mav;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView doLogin(@RequestParam String userId, @RequestParam String orgId, @RequestParam String password) {
        ModelAndView mav = new ModelAndView();
        //TODO 로그인 처리 한다.
        // session.
//        if(memberService.isUserExistsWithPassword(user)) {
//            response.sendError(404, "incorrent user information : " + userId);
//            return;
//        }
//        user = memberService.getUser(userId);
        mav.setViewName("store");
        return mav;
    }


    @RequestMapping(value = "/store")
    public ModelAndView viewStore() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("store");
        return mav;
    }

    @RequestMapping(value = "/rest/login", method = RequestMethod.POST)
    public void doLogin(@RequestBody String json, HttpServletResponse response) {

        try {
            User user = JsonUtil.json2Object(json, User.class);
            logger.debug("user : {}", user);

            String userId = user.getId();

            if(memberService.isUserExistsWithPassword(user)) {
                response.sendError(404, "incorrent user information : " + userId);
                return;
            }
            user = memberService.getUser(userId);
            response.getWriter().print(JsonUtil.object2String(user));
        } catch (IOException e) {
            logger.error("", e);
        }
    }
}
