package org.opencloudengine.serviceportal.controller;

import org.opencloudengine.serviceportal.db.entity.App;
import org.opencloudengine.serviceportal.db.entity.Organization;
import org.opencloudengine.serviceportal.db.entity.Resources;
import org.opencloudengine.serviceportal.db.entity.User;
import org.opencloudengine.serviceportal.service.AppManageService;
import org.opencloudengine.serviceportal.service.GarudaService;
import org.opencloudengine.serviceportal.service.MemberService;
import org.opencloudengine.serviceportal.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by swsong on 2015. 8. 18..
 */

@Controller
public class RestController {
    private static final Logger logger = LoggerFactory.getLogger(RestController.class);

    @Autowired
    private MemberService memberService;

    @Autowired
    AppManageService appManageService;

    @Autowired
    private GarudaService garudaService;

    @RequestMapping(value = "/api/apps/{appId}", method = RequestMethod.HEAD)
    public void apps(@PathVariable String appId, HttpSession session, HttpServletResponse response) throws IOException {
        App app = appManageService.getApp(appId);
        if(app != null) {
            response.setStatus(200);
        } else {
            response.setStatus(404, "no such app : " + appId);
        }
    }

    @RequestMapping(value = "/api/apps/{appId}/apply", method = RequestMethod.POST)
    public void appApply(@PathVariable String appId, HttpServletResponse response) {



        String app = "";
        if(app != null) {
            response.setStatus(200);
        } else {
            response.setStatus(404, "no such app : " + appId);
        }
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
