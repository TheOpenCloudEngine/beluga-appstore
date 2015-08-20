package org.opencloudengine.serviceportal.controller;

import org.opencloudengine.serviceportal.db.entity.*;
import org.opencloudengine.serviceportal.service.AppManageService;
import org.opencloudengine.serviceportal.service.GarudaService;
import org.opencloudengine.serviceportal.service.MemberService;
import org.opencloudengine.serviceportal.util.DateUtil;
import org.opencloudengine.serviceportal.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

/**
 * Created by swsong on 2015. 8. 18..
 */

@Controller
public class RestController {
    private static final Logger logger = LoggerFactory.getLogger(RestController.class);

    private static final String clusterId = "test-cluster";

    @Autowired
    private MemberService memberService;

    @Autowired
    AppManageService appManageService;

    @Autowired
    private GarudaService garudaService;

    @RequestMapping(value = "/api/apps/{appId}", method = RequestMethod.HEAD)
    public void apps(@PathVariable String appId, HttpSession session, HttpServletResponse response) throws IOException {
        App app = appManageService.getApp(appId);
        if (app != null) {
            response.setStatus(200);
        } else {
            response.setStatus(404, "no such app : " + appId);
        }
    }

    @RequestMapping(value = "/api/apps/upload", method = RequestMethod.POST)
    public void uploadAppFile(@RequestParam("file") MultipartFile file, HttpServletResponse response, HttpSession session) throws IOException {
        User user = (User) session.getAttribute(User.USER_KEY);
        String orgId = user.getOrgId();
        File appFile = appManageService.saveMultipartFile(file, orgId);

        if (appFile != null) {
            // garuda에 올린다.
            String filePath = garudaService.uploadAppFile(clusterId, orgId, appFile);
            if (filePath != null) {
                long length = appFile.length();
                String fileName = appFile.getName();
                UploadFile uploadFile = new UploadFile(fileName, filePath, length, DateUtil.getNow());
                response.setCharacterEncoding("utf-8");
                response.getWriter().print(JsonUtil.object2String(uploadFile));
                return;
            } else {
                response.sendError(500, "Cannot upload file to remote garuda server.");
            }
        } else {
            response.sendError(500, "File is empty");
        }
    }

    @RequestMapping(value = "/api/apps/{appId}/deploy", method = RequestMethod.POST)
    public void appDeploy(@PathVariable String appId, HttpServletResponse response) throws Exception {

        try {
            App app = appManageService.getApp(appId);

            if (app != null) {
                if (garudaService.deployApp(clusterId, app)) {
                    response.setStatus(200);
                    return;
                } else {
                    response.sendError(500, "error : " + appId);
                }
            } else {
                response.sendError(404, "no such app : " + appId);
            }
        } catch (Exception e) {
//            logger.error("", e);
//            response.sendError(500, "error : " + e.getMessage());
//            response.sendError(500, "error : " + e.getMessage());
            response.setStatus(500);
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(e.getMessage());
        }
    }

    @RequestMapping(value = "/api/apps/{appId}", method = RequestMethod.DELETE)
    public void deleteApp(@PathVariable String appId, HttpServletResponse response) throws IOException {
        ModelAndView mav = new ModelAndView();
        if (garudaService.destoryApp(clusterId, appId)) {
            appManageService.deleteApp(appId);
            response.setStatus(200);
            return;
        } else {
            response.sendError(500, "no such app : " + appId);
        }
    }

    @RequestMapping(value = "/api/user/{userId:.+}", method = RequestMethod.HEAD)
    public void testUser(@PathVariable String userId, HttpServletResponse response) throws IOException {
        User user = memberService.getUser(userId);
        if (user != null) {
            response.setStatus(200);
        } else {
            response.sendError(404, "no such user : " + userId);
        }
    }

    @RequestMapping(value = "/api/organization/{orgId}", method = RequestMethod.GET)
    public void testOrganization(@PathVariable String orgId, HttpServletResponse response) throws IOException {
        Organization organization = memberService.getOrganization(orgId);
        if (organization != null) {
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
        if (!memberService.isUserExistsWithPassword(user)) {
            response.sendError(401, "Incorrect user information : " + id);
            return;
        }
        user = memberService.getUser(id);
        String orgId = user.getOrgId();
        if (appManageService.isGranted(orgId, appId)) {
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

    @RequestMapping(value = "/api/subscribe/{appId}", method = RequestMethod.POST)
    @ResponseBody
    public String subscribe(@PathVariable String appId, HttpServletResponse response, HttpSession session) {
        User user = (User) session.getAttribute(User.USER_KEY);
        if(!user.getType().equals(User.ADMIN_TYPE)) {
            return "Only admin can subscribe apps.";
        }

        String orgId = user.getOrgId();
        if(appManageService.isGranted(orgId, appId)) {
            return "Already subscribed.";
        }

        appManageService.setGrant(orgId, appId);
        return "[SUCCESS] Now your organization members can use this app.";
    }

}
