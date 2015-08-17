package org.opencloudengine.serviceportal.controller;

import org.opencloudengine.serviceportal.db.entity.*;
import org.opencloudengine.serviceportal.service.AppManageService;
import org.opencloudengine.serviceportal.service.GarudaService;
import org.opencloudengine.serviceportal.service.MemberService;
import org.opencloudengine.serviceportal.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * Created by swsong on 2015. 8. 16..
 */

@Controller
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private MemberService memberService;

    @Autowired
    private AppManageService appManageService;

    @Autowired
    private GarudaService garudaService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView root() throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/index");
        return mav;
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/store");
        return mav;
    }

    @RequestMapping(value = "/store", method = RequestMethod.GET)
    public ModelAndView viewStore() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("store");
        return mav;
    }

    @RequestMapping(value = "/api/user/{userId:.+}", method = RequestMethod.HEAD)
    public void testUser(@PathVariable String userId, HttpServletResponse response) throws IOException {
        User user = memberService.getUser(userId);
        if(user != null) {
            response.setStatus(200);
        } else {
            response.sendError(404, "no such user : " + userId);
        }
    }

    @RequestMapping(value = "/api/organization/{orgId}", method = RequestMethod.GET)
    public void testOrganization(@PathVariable String orgId, HttpServletResponse response) throws IOException {
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
        ModelAndView mav = new ModelAndView();

        //0. 사전점검.
        User user = memberService.getUser(userId);
        if(user != null) {
            //이메일이 중복이면, 다시 등록화면.
            mav.setViewName("redirect:/signUp");
            return mav;
        }

        //1. org 등록.

        mav.setViewName("index");

        boolean isAdmin = false;
        Organization organization = memberService.getOrganization(orgId);
        if(organization == null) {
            organization = new Organization(orgId, orgName);
            memberService.addOrganization(organization);
            isAdmin = true;
        }

        //2. id 등록.
        user = new User(userId, orgId, isAdmin? User.ADMIN_TYPE : User.USER_TYPE);
        user.setPassword(password);
        memberService.addUser(user);
        mav.setViewName("redirect:/login");
        return mav;
    }

    @RequestMapping(value = "/user", method = RequestMethod.DELETE)
    public ModelAndView deleteUser(@RequestParam String userId) {
        ModelAndView mav = new ModelAndView();

        //TODO ajax로 관리자여부를 확인하여 관리자이면 삭제가 안되도록. 다른 사람을 관리자로 먼저 지정필요.

        return mav;
    }

    @RequestMapping(value = "/organization", method = RequestMethod.DELETE)
    public ModelAndView deleteOrganization(@RequestParam String orgId) {
        ModelAndView mav = new ModelAndView();

        //TODO 하위 사용자까지 모두 삭제.

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

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public void loginAPI(@RequestBody String appId, @RequestBody String id, @RequestBody String password, HttpServletResponse response) throws IOException {

        User user = new User();
        user.setId(id);
        user.setPassword(password);
        if(!memberService.isUserExistsWithPassword(user)) {
            response.sendError(401, "Incorrect user information : " + id);
            return;
        }
        user = memberService.getUser(id);
        String orgId = user.getOrgId();
        if(appManageService.isGranted(orgId, appId)) {
            // 사용가능한 리소스 정보를 전달.
            Resources allResources = garudaService.getResources();
            //조직별 사용할 리소스를 산출한다.
            Resources usingResources = appManageService.getUsingResources(appId, allResources);

            response.getWriter().print(JsonUtil.object2String(usingResources));
        } else {
            response.sendError(403, "App " + appId + " is not allowed to organization " + orgId);
        }

    }

    @RequestMapping(value = "/api/oauth2/token", method = RequestMethod.POST)
    public void oauth2Token(@RequestBody String json, HttpServletResponse response) throws IOException {
       //TODO
    }
    @RequestMapping(value = "/api/oauth2/authorization", method = RequestMethod.POST)
    public void oauth2Authrozation(@RequestBody String json, HttpServletResponse response) throws IOException {
        //TODO
    }
}
