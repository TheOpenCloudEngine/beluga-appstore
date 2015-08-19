package org.opencloudengine.serviceportal.controller;

import org.opencloudengine.serviceportal.db.entity.*;
import org.opencloudengine.serviceportal.service.AppManageService;
import org.opencloudengine.serviceportal.service.GarudaService;
import org.opencloudengine.serviceportal.service.MemberService;
import org.opencloudengine.serviceportal.util.DateUtil;
import org.opencloudengine.serviceportal.util.JsonUtil;
import org.opencloudengine.serviceportal.util.ParseUtil;
import org.opencloudengine.serviceportal.util.SizeUnit;
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
import javax.ws.rs.NotFoundException;
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
    private AppManageService appManageService;

    @Autowired
    private MemberService memberService;

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
            app.setApplyDate(DateUtil.convertDateString(app.getApplyDate()));
        }
    }

    @RequestMapping(value = "/apps/{appId}/edit", method = RequestMethod.GET)
    public ModelAndView appEdit(@PathVariable String appId) {
        App app = appManageService.getApp(appId);
        if(app == null) {
            throw new NotFoundException();
        }
        ModelAndView mav = new ModelAndView();
        mav.addObject("app", app);
        app.setAppFileLengthDisplay(ParseUtil.toHumanSize(app.getAppFileLength()));
        mav.setViewName("o/appEdit");
        return mav;
    }

    @RequestMapping(value = "/apps/{appId}/edit", method = RequestMethod.POST)
    public ModelAndView appEditUpdate(@PathVariable String appId) {


        //TODO db에 업데이트.


        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/o/apps/" + appId);
        return mav;
    }

    @RequestMapping(value = "/apps/{appId}", method = RequestMethod.GET)
    public ModelAndView appInfo(@PathVariable String appId) {
        App app = appManageService.getApp(appId);
        if(app == null) {
            throw new NotFoundException();
        }
        String elapsed = DateUtil.getElapsedTime(app.getApplyDate());
        ModelAndView mav = new ModelAndView();
        mav.addObject("elapsed", elapsed);
        mav.addObject("app", app);
        app.setAppFileLengthDisplay(ParseUtil.toHumanSize(app.getAppFileLength()));
        app.setMemoryDisplay(ParseUtil.toHumanSizeOverMB(app.getMemory() * SizeUnit.MB));
        mav.setViewName("o/appInfo");
        return mav;
    }

    @RequestMapping(value = "/apps/new", method = RequestMethod.GET)
    public ModelAndView appNew() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("o/appNew");
        return mav;
    }


    @RequestMapping(value = "/apps", method = RequestMethod.POST)
    public ModelAndView appNewCreate(@RequestParam Map<String, Object> data, HttpSession session) {

        logger.debug("appNew data : {}", data);
        User user = getUser(session);
        String orgId = user.getOrgId();

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
        app.setScale(ParseUtil.parseInt(scale));

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
        mav.setViewName("redirect:/o/apps/" + id);
        return mav;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView profile(HttpSession session) {

        User user = (User) session.getAttribute(User.USER_KEY);
        Organization organization = memberService.getOrganization(user.getOrgId());
        ModelAndView mav = new ModelAndView();
        mav.addObject("user", user);

        if(user.getType().equals(User.ADMIN_TYPE)) {
            mav.addObject("userType", "Administrator");
        } else {
            mav.addObject("userType", "User");
        }
        String joinDate = DateUtil.getShortDateString(user.getJoinDate());
        mav.addObject("joinDate", joinDate);
        mav.addObject("orgName", organization.getName());
        mav.setViewName("o/profile");
        return mav;
    }

    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    public ModelAndView settings(HttpSession session) {
        User user = (User) session.getAttribute(User.USER_KEY);
        Organization organization = memberService.getOrganization(user.getOrgId());
        List<User> users = memberService.getUsers(organization.getId());
        ModelAndView mav = new ModelAndView();
        String joinDate = DateUtil.getShortDateString(organization.getJoinDate());
        mav.addObject("joinDate", joinDate);
        mav.addObject("organization", organization);
        mav.addObject("users", users);
        mav.setViewName("o/settings");
        return mav;
    }
}
