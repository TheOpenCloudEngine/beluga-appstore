package org.opencloudengine.serviceportal.controller;

import org.opencloudengine.serviceportal.db.entity.App;
import org.opencloudengine.serviceportal.db.entity.UploadFile;
import org.opencloudengine.serviceportal.db.entity.User;
import org.opencloudengine.serviceportal.service.AppManageService;
import org.opencloudengine.serviceportal.util.DateUtil;
import org.opencloudengine.serviceportal.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by swsong on 2015. 8. 17..
 */

@Controller
@RequestMapping("/o")
public class OrgController {
    private static final Logger logger = LoggerFactory.getLogger(OrgController.class);

    @Autowired
    AppManageService appManageService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/o/apps");
        return mav;
    }

    @RequestMapping(value = "/apps", method = RequestMethod.GET)
    public ModelAndView apps() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("o/apps");
        return mav;
    }

    @RequestMapping(value = "/appEdit", method = RequestMethod.GET)
    public ModelAndView appEdit() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("o/appEdit");
        return mav;
    }

    @RequestMapping(value = "/appEdit", method = RequestMethod.POST)
    public ModelAndView appEditUpdate() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:appInfo");
        return mav;
    }

    @RequestMapping(value = "/appInfo", method = RequestMethod.GET)
    public ModelAndView appInfo() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("o/appInfo");
        return mav;
    }

    @RequestMapping(value = "/appNew", method = RequestMethod.GET)
    public ModelAndView appNew() {
        ModelAndView mav = new ModelAndView();



        mav.setViewName("o/appNew");
        return mav;
    }

    @RequestMapping(value = "/appNew", method = RequestMethod.POST)
    public ModelAndView appNewCreate(@RequestParam Map<String, Object> data) {
        ModelAndView mav = new ModelAndView();
        logger.debug("appNew data : {}", data);
        //TODO 페이지에서 ID 중복여부를 확인한다.
        String id = (String) data.get("id");
        String orgId = (String) data.get("orgId");
        String name = (String) data.get("name");
        String description = (String) data.get("description");
        String appFile = (String) data.get("appFile");
        String appFileDate = (String) data.get("appFileDate"); //Version
        String appFileSize = (String) data.get("appFileSize");
//        String appFileUpdated = (String) data.get("appFileUpdated");
        String cpus = (String) data.get("cpus");
        String memory = (String) data.get("memory");
        String scale = (String) data.get("scale");
//        String version = (String) data.get("version");

        String resources = (String) data.get("resources");
        String autoScaleOutUse = (String) data.get("autoScaleOutUse");
        String autoScaleInUse = (String) data.get("autoScaleInUse");
        String autoScaleOutConf = (String) data.get("autoScaleOutConf");
        String autoScaleInConf = (String) data.get("autoScaleInConf");

        App app = new App();
        app.setId(id);
        app.setOrgId(orgId);
        //TODO
        appManageService.createApp(app);



        mav.setViewName("redirect:appInfo");
        return mav;
    }

    @RequestMapping(value = "/uploadAppFile", method = RequestMethod.POST)
    public void uploadAppFile(@RequestParam("file") MultipartFile file, HttpServletResponse response, HttpSession session) throws IOException {
        User user = (User) session.getAttribute(User.USER_KEY);
        String orgId = user.getOrgId();
        File appFile = appManageService.uploadAppFile(file, orgId);

        if(appFile != null) {
            long length = appFile.length();
            String fileName = appFile.getName();
            UploadFile uploadFile = new UploadFile(fileName, length, DateUtil.getNow());
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(JsonUtil.object2String(uploadFile));
        } else {
            response.sendError(500, "File is empty");
        }
    }

    @RequestMapping(value = "/manage", method = RequestMethod.GET)
    public ModelAndView manage() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("o/manage");
        return mav;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView profile() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("o/profile");
        return mav;
    }

    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    public ModelAndView settings() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("o/settings");
        return mav;
    }
}
