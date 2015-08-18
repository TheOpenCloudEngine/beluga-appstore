package org.opencloudengine.serviceportal.controller;

import org.opencloudengine.serviceportal.db.entity.App;
import org.opencloudengine.serviceportal.db.entity.ResourcePlan;
import org.opencloudengine.serviceportal.db.entity.UploadFile;
import org.opencloudengine.serviceportal.db.entity.User;
import org.opencloudengine.serviceportal.service.AppManageService;
import org.opencloudengine.serviceportal.service.GarudaService;
import org.opencloudengine.serviceportal.util.DateUtil;
import org.opencloudengine.serviceportal.util.JsonUtil;
import org.opencloudengine.serviceportal.util.ParseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;
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

    @Autowired
    GarudaService garudaService;

    private User getUser(HttpSession session) {
        return (User) session.getAttribute(User.USER_KEY);
    }
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/o/apps");
        return mav;
    }

    @RequestMapping(value = "/apps", method = RequestMethod.GET)
    public ModelAndView apps(HttpSession session) {
        User user = getUser(session);
        String orgId = user.getOrgId();
        List<App> appList = appManageService.getOrgApps(orgId);
        List<App> outerAppList = appManageService.getOuterApps(orgId);
        makeUserFriendlyApp(appList);
        makeUserFriendlyApp(outerAppList);
        ModelAndView mav = new ModelAndView();
        mav.addObject("appList", appList);
        mav.addObject("outerAppList", outerAppList);
        mav.setViewName("o/apps");
        return mav;
    }

    @RequestMapping(value = "/manage", method = RequestMethod.GET)
    public ModelAndView manage(HttpSession session) {
        User user = getUser(session);
        String orgId = user.getOrgId();
        List<App> appList = appManageService.getOrgApps(orgId);
        List<App> outerAppList = appManageService.getOuterApps(orgId);
        makeUserFriendlyApp(appList);
        makeUserFriendlyApp(outerAppList);
        ModelAndView mav = new ModelAndView();
        int appSize = appList != null ? appList.size() : 0;
        int outerAppSize = outerAppList != null ? outerAppList.size() : 0;
        float totalCpus = 0;
        int totalMemory = 0;

        if(appList != null) {
            for(App app : appList) {
                totalCpus += app.getCpus();
                totalMemory += app.getMemory();
            }
        }
        String totalMemoryString = ParseUtil.toHumanSizeOverMB(totalMemory * 1000000L);

        mav.addObject("appSize", appSize);
        mav.addObject("outerAppSize", outerAppSize);
        mav.addObject("totalCpus", totalCpus);
        mav.addObject("totalMemory", totalMemoryString);
        mav.addObject("appList", appList);
        mav.addObject("outerAppList", outerAppList);
        mav.setViewName("o/manage");
        return mav;
    }
    private void makeUserFriendlyApp(List<App> appList) {
        if(appList == null) {
            return;
        }
        // applied date "yyyy-MM-dd hh:mm:ss" => "yyyy.MM.dd"
        for(App app : appList) {
            app.setAppliedDate(DateUtil.convertDateString(app.getAppliedDate()));
        }
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
    public ModelAndView appNewCreate(@RequestParam Map<String, Object> data, HttpSession session) {

        logger.debug("appNew data : {}", data);
        User user = getUser(session);
        String orgId = user.getOrgId();

        //TODO 페이지에서 ID 중복여부를 확인한다.

        String id = (String) data.get("id");
        String name = (String) data.get("name");
        String description = (String) data.get("description");
        String appFile = (String) data.get("fileName");
        String appFilePath = (String) data.get("filePath");
        String appFileLength = (String) data.get("fileLength");
        String appFileDate = (String) data.get("fileDate");
        String environment = (String) data.get("environment");
        String cpus = (String) data.get("cpus");
        String memory = (String) data.get("memory");
        String scale = (String) data.get("scale");

        Integer dbResourceSize = ParseUtil.parseInt(data.get("db_resource_size"));
        Integer ftpResourceSize = ParseUtil.parseInt(data.get("ftp_resource_size"));

        String resources = (String) data.get("resources");
        String autoScaleOutUse = (String) data.get("autoScaleOutUse");
        Integer cpuHigher = ParseUtil.parseInt(data.get("cpuHigher"));
        Integer cpuHigherDuring = ParseUtil.parseInt(data.get("cpuHigherDuring"));
        Integer autoScaleOutSize = ParseUtil.parseInt(data.get("autoScaleOutSize"));
        String autoScaleInUse = (String) data.get("autoScaleInUse");
        Integer cpuLower = ParseUtil.parseInt(data.get("cpuLower"));
        Integer cpuLowerDuring = ParseUtil.parseInt(data.get("cpuLowerDuring"));

        App app = new App();
        app.setId(id);
        app.setOrgId(orgId);
        app.setName(name);
        app.setDescription(description);
        app.setAppFile(appFile);
        app.setAppFilePath(appFilePath);
        app.setAppFileLength(ParseUtil.parseLong(appFileLength));
        app.setAppFileDate(appFileDate);
        app.setEnvironment(environment);
        app.setCpus(ParseUtil.parseFloat(cpus));
        app.setMemory(ParseUtil.parseInt(memory));
        app.setScale(ParseUtil.parseFloat(scale));

        /* resources plan */
        App.ResourcesPlan resourcesPlan = new App.ResourcesPlan();
        String dbPrefix = "db";
        String ftpPrefix = "ftp";
        String optionSuffix = "_option";
        for (int i = 0; i < dbResourceSize; i++){
            String key = dbPrefix + i;
            String dbId = (String) data.get(key);
            if(dbId != null) {
                String optionKey = key + optionSuffix;
                String option = (String) data.get(optionKey);
                ResourcePlan p = new ResourcePlan(dbId, option);
                resourcesPlan.addPlan(p);
            }
        }
        for (int i = 0; i < ftpResourceSize; i++){
            String key = ftpPrefix + i;
            String ftpId = (String) data.get(key);
            if(ftpId != null) {
                String optionKey = key + optionSuffix;
                String option = (String) data.get(optionKey);
                ResourcePlan p = new ResourcePlan(ftpId, option);
                resourcesPlan.addPlan(p);
            }
        }
        app.setResourcesPlan(resourcesPlan);

        /* auto scale */
        App.AutoScaleOutConfig autoScaleOutConfig = new App.AutoScaleOutConfig();
        autoScaleOutConfig.setCpuHigher(cpuHigher);
        autoScaleOutConfig.setCpuHigherDuring(cpuHigherDuring);
        autoScaleOutConfig.setAddScale(autoScaleOutSize);

        App.AutoScaleInConfig autoScaleInConfig = new App.AutoScaleInConfig();
        autoScaleInConfig.setCpuLower(cpuLower);
        autoScaleInConfig.setCpuLowerDuring(cpuLowerDuring);

        app.setAutoScaleOutUse(ParseUtil.parseChar(autoScaleOutUse));
        app.setAutoScaleInUse(ParseUtil.parseChar(autoScaleInUse));
        app.setAutoScaleOutConfig(autoScaleOutConfig);
        app.setAutoScaleInConfig(autoScaleInConfig);

        appManageService.createApp(app);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:appInfo");
        return mav;
    }

    @RequestMapping(value = "/uploadAppFile", method = RequestMethod.POST)
    public void uploadAppFile(@RequestParam("file") MultipartFile file, HttpServletResponse response, HttpSession session) throws IOException {
        User user = (User) session.getAttribute(User.USER_KEY);
        String orgId = user.getOrgId();
        File appFile = appManageService.saveMultipartFile(file, orgId);

        if(appFile != null) {
            String clusterId = "dev";
            // garuda에 올린다.
            String filePath = garudaService.uploadAppFile(clusterId, orgId, appFile);
            if(filePath != null) {
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
