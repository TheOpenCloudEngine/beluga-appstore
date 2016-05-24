package org.opencloudengine.garuda.belugaservice.controller;

import org.opencloudengine.garuda.belugaservice.db.entity.*;
import org.opencloudengine.garuda.belugaservice.entity.AppStatus;
import org.opencloudengine.garuda.belugaservice.entity.LibertyImageProvided;
import org.opencloudengine.garuda.belugaservice.service.*;
import org.opencloudengine.garuda.belugaservice.util.DateUtil;
import org.opencloudengine.garuda.belugaservice.util.ParseUtil;
import org.opencloudengine.garuda.belugaservice.util.SizeUnit;
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

import javax.servlet.http.HttpSession;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
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
    private ResourceManageService resourceManageService;

    @Autowired
    private ResourceTypeService resourceTypeService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private BelugaService belugaService;

    @Autowired
    private LibertyImageService imageService;

    private User getUser(HttpSession session) {
        return (User) session.getAttribute(User.USER_KEY);
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/o/apps");
        return mav;
    }

    /*
    * 모든 앱 정보 받기
    * */
    @RequestMapping(value = "/apps", method = RequestMethod.GET)
    public ModelAndView apps(HttpSession session) {
        User user = getUser(session);
        String orgId = user.getOrgId();
        List<App> appList = appManageService.getOrgApps(orgId);
        makeUserFriendlyApp(appList);
        ModelAndView mav = new ModelAndView();
        mav.addObject("domain", belugaService.getDomainName());
        mav.addObject("appList", appList);
        mav.setViewName("o/apps");
        return mav;
    }

    /*
    * 매니지 화면
    * */
    @RequestMapping(value = "/manage", method = RequestMethod.GET)
    public ModelAndView manage(HttpSession session) {
        User user = getUser(session);
        String orgId = user.getOrgId();
        List<App> appList = appManageService.getOrgApps(orgId);
        makeUserFriendlyApp(appList);
        List<Resource> resources = resourceManageService.getOrgResources(orgId);
        for (Resource r : resources) {
            r.fillCreateDateDisplay(DateUtil.getDateFormat2());
        }

        for (App app : appList) {
            AppStatus status = belugaService.getAppStatus(app.getId());
            if (status != null) {
                app.setAppStatus(status.getStatus());
            }
        }
        for (Resource resource : resources) {
            AppStatus status = belugaService.getAppStatus(resource.getId());
            if (status != null) {
                resource.setAppStatus(status.getStatus());
            }
        }

        ModelAndView mav = new ModelAndView();
        int appSize = appList != null ? appList.size() : 0;
        float totalCpus = 0;
        int totalMemory = 0;

        if (appList != null) {
            for (App app : appList) {
                totalCpus += app.getCpus();
                totalMemory += app.getMemory() * app.getScale();
            }
        }
        String totalMemoryString = ParseUtil.toHumanSizeOverMB(totalMemory * 1000000L);

        mav.addObject("appSize", appSize);
        mav.addObject("totalCpus", totalCpus);
        mav.addObject("totalMemory", totalMemoryString);
        mav.addObject("appList", appList);
        mav.addObject("domain", belugaService.getDomainName());
        mav.addObject("resources", resources);
        mav.setViewName("o/manage");
        return mav;
    }

    private void makeUserFriendlyApp(List<App> appList) {
        if (appList == null) {
            return;
        }
        // applied date "yyyy.MM.dd"
        for (App app : appList) {
            app.fillUpdateDateDisplay(DateUtil.getShortDateFormat());
        }
    }


    /*
    * 특정 앱 정보 받기
    * */
    @RequestMapping(value = "/apps/{appId}/{version}", method = RequestMethod.GET)
    public ModelAndView appInfo(@PathVariable String appId, @PathVariable Integer version) {
        App app = appManageService.getApp(appId, version);
        if (app == null) {
            throw new NotFoundException();
        }
        ModelAndView mav = new ModelAndView();
        app.setAppFileLengthDisplay(ParseUtil.toHumanSize(app.getAppFileLength()));
        app.setAppFileLengthDisplay2(ParseUtil.toHumanSize(app.getAppFileLength2()));
        app.setMemoryDisplay(ParseUtil.toHumanSizeOverMB(app.getMemory() * SizeUnit.MB));

        List<App> apps = appManageService.getAppsById(appId);
        mav.addObject("apps", apps);

        mav.addObject("app", app);
        mav.addObject("domain", belugaService.getDomainName());
        List<String> resourceList = app.getResourceList();
        List<Resource> resources = new ArrayList<>();
        if (resourceList != null) {
            for (String rid : resourceList) {
                Resource resource = resourceManageService.getResource(rid);
                resources.add(resource);
            }
        }
        mav.addObject("resources", resources);
        mav.setViewName("o/appInfo");
        return mav;
    }

    /*
    * 앱 수정 화면.
    * */
    @RequestMapping(value = "/apps/{appId}/{version}/edit", method = RequestMethod.GET)
    public ModelAndView appEdit(@PathVariable String appId, @PathVariable Integer version) {
        App app = appManageService.getApp(appId, version);
        if (app == null) {
            throw new NotFoundException();
        }
        ModelAndView mav = new ModelAndView();
        mav.addObject("domain", belugaService.getDomainName());
        mav.addObject("app", app);
        app.setAppFileLengthDisplay(ParseUtil.toHumanSize(app.getAppFileLength()));

        String orgId = app.getOrgId();
        List<Resource> resources = resourceManageService.getOrgResources(orgId);
        //사용중 체크.
        List<String> inUseResources = app.getResourceList();
        for (Resource resource : resources) {
            if (inUseResources.contains(resource.getId())) {
                resource.setInUse(true);
            }
        }
        mav.addObject("resources", resources);
        mav.setViewName("o/appEdit");
        return mav;
    }

    /*
    * 앱 수정후 저장.
    * */
    @RequestMapping(value = "/apps/{appId}/{version}/edit", method = RequestMethod.POST)
    public ModelAndView appEditUpdate(@PathVariable String appId, @PathVariable Integer version, @RequestParam Map<String, Object> data, HttpSession session) {
        User user = getUser(session);
        String orgId = user.getOrgId();
        data.put("id", appId);
        data.put("orgId", orgId);
        data.put("version", version);
        appManageService.updateApp(data);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/o/apps/" + appId + "/" + version);
        return mav;
    }

    /*
   * 앱 신규생성 화면
   * */
    @RequestMapping(value = "/apps/new", method = RequestMethod.GET)
    public ModelAndView appNew(HttpSession session) {
        User user = getUser(session);
        String orgId = user.getOrgId();

        ModelAndView mav = new ModelAndView();
        mav.setViewName("o/appNew");
        mav.addObject("domain", belugaService.getDomainName());
        mav.addObject("resources", resourceManageService.getOrgResources(orgId));
        return mav;
    }

    /*
    * 앱 신규생성 저장.
    * */
    @RequestMapping(value = "/apps", method = RequestMethod.POST)
    public ModelAndView appNewCreate(@RequestParam Map<String, Object> data, HttpSession session) {
        User user = getUser(session);
        String orgId = user.getOrgId();
        data.put("orgId", orgId);
        logger.debug("appNew data : {}", data);

        String id = appManageService.createApp(data);
        App app = appManageService.selectMaxVersion(id);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/o/apps/" + id + "/" + app.getVersion());
        return mav;
    }

    /*
    * 앱 버젼 신규생성 화면
    * */
    @RequestMapping(value = "/apps/{appId}/{version}/new", method = RequestMethod.GET)
    public ModelAndView appVersionNew(@PathVariable String appId, @PathVariable Integer version, HttpSession session) {

        //카피할 app 정보를 얻어온다.
        App app = appManageService.getApp(appId, version);
        if (app == null) {
            throw new NotFoundException();
        }

        ModelAndView mav = new ModelAndView();
        mav.addObject("domain", belugaService.getDomainName());
        mav.addObject("app", app);
        app.setAppFileLengthDisplay(ParseUtil.toHumanSize(app.getAppFileLength()));

        String orgId = app.getOrgId();
        List<Resource> resources = resourceManageService.getOrgResources(orgId);
        //사용중 체크.
        List<String> inUseResources = app.getResourceList();
        for (Resource resource : resources) {
            if (inUseResources.contains(resource.getId())) {
                resource.setInUse(true);
            }
        }
        mav.addObject("resources", resources);
        mav.setViewName("o/appVersionNew");
        return mav;
    }

    /*
    * 앱 버젼 신규생성 저장.
    * */
    @RequestMapping(value = "/apps/{appId}", method = RequestMethod.POST)
    public ModelAndView appVersionNewCreate(@PathVariable String appId, @RequestParam Map<String, Object> data, HttpSession session) {
        User user = getUser(session);
        String orgId = user.getOrgId();
        data.put("id", appId);
        data.put("orgId", orgId);
        logger.debug("appNew data : {}", data);

        String id = appManageService.createVersion(data);
        App app = appManageService.selectMaxVersion(id);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/o/apps/" + id + "/" + app.getVersion());
        return mav;
    }

    /*
    * 특정 리소스 정보 받기
    * */
    @RequestMapping(value = "/resources/{resourceId}", method = RequestMethod.GET)
    public ModelAndView resourceInfo(@PathVariable String resourceId) {
        Resource resource = resourceManageService.getResource(resourceId);
        if (resource == null) {
            throw new NotFoundException();
        }
        resource.setMemoryDisplay(ParseUtil.toHumanSizeOverMB(resource.getMemory() * SizeUnit.MB));
        resource.fillCreateDateDisplay(DateUtil.getDateFormat2());
        ModelAndView mav = new ModelAndView();
        mav.addObject("resource", resource);
        mav.addObject("domain", belugaService.getDomainName());
        String[] hostPort = belugaService.getAppHostPort(resourceId);
        mav.addObject("hostPort", hostPort);
        mav.setViewName("o/resourceInfo");
        return mav;
    }

    /*
    * 리소스 신규생성 화면
    * */
    @RequestMapping(value = "/resources/new", method = RequestMethod.GET)
    public ModelAndView resourceNew(@RequestParam String resourceTypeId, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("o/resourceNew");
        User user = getUser(session);
        String orgId = user.getOrgId();
        //List<Resource> usingResources = resourceManageService.getOrgResources(orgId);
        //mav.addObject("usingResources", usingResources);
        //mav.addObject("allResources", ResourceProvided.resourceProvidedList);

        ResourceType resourceType = resourceTypeService.getResourceType(resourceTypeId);
        mav.addObject("resourceType", resourceType);
        return mav;
    }


    /*
    * 리소스 신규생성 저장.
    * */
    @RequestMapping(value = "/resources", method = RequestMethod.POST)
    public ModelAndView resourceNewCreate(@RequestParam Map<String, Object> data, HttpSession session) {
        User user = getUser(session);
        String orgId = user.getOrgId();
        data.put("orgId", orgId);
        logger.debug("resourceNew data : {}", data);

        String id = resourceManageService.createResource(data);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/o/resources/" + id);
        return mav;
    }

    /*
    * 리소스타입 서비스 화면
    * */
    @RequestMapping(value = "/service", method = RequestMethod.GET)
    public ModelAndView service(HttpSession session) {
        User user = getUser(session);
        String orgId = user.getOrgId();
        List<ResourceType> allResourceTypes = resourceTypeService.getAllResourceTypes();
        for (ResourceType r : allResourceTypes) {
            r.fillCreateDateDisplay(DateUtil.getDateFormat2());
        }

        ModelAndView mav = new ModelAndView();
        mav.addObject("allResourceTypes", allResourceTypes);
        mav.setViewName("o/service");
        return mav;
    }

    /*
    * 특정 리소스타입 정보 받기
    * */
    @RequestMapping(value = "/resourcetype/{id}", method = RequestMethod.GET)
    public ModelAndView resourceTypeInfo(@PathVariable String id) {
        ResourceType resourceType = resourceTypeService.getResourceType(id);
        if (resourceType == null) {
            throw new NotFoundException();
        }
        resourceType.fillCreateDateDisplay(DateUtil.getDateFormat2());
        ModelAndView mav = new ModelAndView();
        mav.addObject("resourceType", resourceType);
        mav.setViewName("o/resourceTypeInfo");
        return mav;
    }

    /*
    * 리소스타입 신규생성 화면
    * */
    @RequestMapping(value = "/resourcetype/new", method = RequestMethod.GET)
    public ModelAndView resourceTypeNew(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("o/resourceTypeNew");
        User user = getUser(session);
        String orgId = user.getOrgId();

        List<LibertyImage> libertyImages = imageService.getAllLibertyImages();
        mav.addObject("libertyImages", libertyImages);

        return mav;
    }

    /*
    * 리소스타입 신규생성 저장.
    * */
    @RequestMapping(value = "/resourcetype", method = RequestMethod.POST)
    public ModelAndView resourceTypeNewCreate(ResourceType resourceType, HttpSession session) {
        String id = resourceTypeService.createResource(resourceType);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/o/resourcetype/" + id);
        return mav;
    }

    /*
    * 리소스 타입 수정 화면.
    * */
    @RequestMapping(value = "/resourcetype/{id}/edit", method = RequestMethod.GET)
    public ModelAndView resourceTypeEdit(@PathVariable String id) {
        ResourceType resourceType = resourceTypeService.getResourceType(id);
        if (resourceType == null) {
            throw new NotFoundException();
        }
        resourceType.fillCreateDateDisplay(DateUtil.getDateFormat2());
        ModelAndView mav = new ModelAndView();
        mav.addObject("resourceType", resourceType);
        mav.setViewName("o/resourceTypeEdit");

        List<LibertyImage> libertyImages = imageService.getAllLibertyImages();
        mav.addObject("libertyImages", libertyImages);

        return mav;
    }

    /*
    * 리소스 타입 수정후 저장.
    * */
    @RequestMapping(value = "/resourcetype/{id}/edit", method = RequestMethod.POST)
    public ModelAndView resourceTypeEditUpdate(@PathVariable String id, ResourceType resourceType, HttpSession session) {

        resourceTypeService.updateResourceType(resourceType);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/o/resourcetype/" + id);
        return mav;
    }


    /*
    * 이미지 화면
    * */
    @RequestMapping(value = "/image", method = RequestMethod.GET)
    public ModelAndView images(HttpSession session) {
        User user = getUser(session);
        String orgId = user.getOrgId();
        List<LibertyImage> images = imageService.getAllLibertyImages();
        for (LibertyImage image : images) {
            image.fillCreateDateDisplay(DateUtil.getDateFormat2());
        }

        ModelAndView mav = new ModelAndView();
        mav.addObject("images", images);
        mav.setViewName("o/images");
        return mav;
    }

    /*
    * 특정 이미지 정보 받기
    * */
    @RequestMapping(value = "/image/{id}/{tag}", method = RequestMethod.GET)
    public ModelAndView imageInfo(@PathVariable String id, @PathVariable String tag) {
        LibertyImage libertyImage = imageService.getLibertyImage(id, tag);
        if (libertyImage == null) {
            throw new NotFoundException();
        }
        libertyImage.fillCreateDateDisplay(DateUtil.getDateFormat2());
        ModelAndView mav = new ModelAndView();
        mav.addObject("libertyImage", libertyImage);
        mav.setViewName("o/imageInfo");
        return mav;
    }

    /*
    * 이미지 신규생성 화면
    * */
    @RequestMapping(value = "/image/new", method = RequestMethod.GET)
    public ModelAndView imageNew(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("o/imageNew");
        User user = getUser(session);
        String orgId = user.getOrgId();

        List<LibertyImageProvided> providedList = LibertyImageProvided.libertyImageProvidedList;
        mav.addObject("providedList", providedList);
        return mav;
    }

    /*
    * 이미지 신규생성 저장.
    * */
    @RequestMapping(value = "/image", method = RequestMethod.POST)
    public ModelAndView imageNewCreate(LibertyImage image, HttpSession session) {

        LibertyImage libertyImage = imageService.createLibertyImage(image);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/o/image/" + libertyImage.getId() + "/" + libertyImage.getTag());
        return mav;
    }

    /*
    * 이미지 수정 화면.
    * */
    @RequestMapping(value = "/image/{id}/{tag}/edit", method = RequestMethod.GET)
    public ModelAndView imageEdit(@PathVariable String id, @PathVariable String tag) {
        LibertyImage libertyImage = imageService.getLibertyImage(id, tag);
        if (libertyImage == null) {
            throw new NotFoundException();
        }
        libertyImage.fillCreateDateDisplay(DateUtil.getDateFormat2());
        ModelAndView mav = new ModelAndView();
        mav.addObject("libertyImage", libertyImage);
        mav.setViewName("o/imageEdit");

        List<LibertyImageProvided> providedList = LibertyImageProvided.libertyImageProvidedList;
        mav.addObject("providedList", providedList);

        return mav;
    }

    /*
    * 이미지 수정후 저장.
    * */
    @RequestMapping(value = "/image/{id}/{tag}/edit", method = RequestMethod.POST)
    public ModelAndView imageEditUpdate(@PathVariable String id, @PathVariable String tag, LibertyImage image, HttpSession session) {

        LibertyImage existImage = imageService.getLibertyImage(id, tag);
        if (existImage == null) {
            throw new NotFoundException();
        }
        existImage.setId(id);
        existImage.setTag(tag);
        LibertyImage libertyImage = imageService.updateLibertyImage(image);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/o/image/" + libertyImage.getId() + "/" + libertyImage.getTag());
        return mav;
    }

    /*
    * 카달로그 화면
    * */
    @RequestMapping(value = "/catalog", method = RequestMethod.GET)
    public ModelAndView catalog(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("o/catalog");
        User user = getUser(session);
        String orgId = user.getOrgId();

        List<ResourceType> allResourceTypes = resourceTypeService.getAllResourceTypes();
        mav.addObject("allResourceTypes", allResourceTypes);
        return mav;
    }

    /*
    * 사용자 프로필 받기.
    * */
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView profile(HttpSession session) {

        User user = (User) session.getAttribute(User.USER_KEY);
        Organization organization = memberService.getOrganization(user.getOrgId());
        ModelAndView mav = new ModelAndView();
        mav.addObject("user", user);

        if (user.getType().equals(User.ADMIN_TYPE)) {
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

    /*
    * 조직 정보 받기
    * */
    @RequestMapping(value = "/organization", method = RequestMethod.GET)
    public ModelAndView settings(HttpSession session) {
        User user = (User) session.getAttribute(User.USER_KEY);
        Organization organization = memberService.getOrganization(user.getOrgId());
        List<User> users = memberService.getUsers(organization.getId());
        ModelAndView mav = new ModelAndView();
        String joinDate = DateUtil.getShortDateString(organization.getJoinDate());
        mav.addObject("joinDate", joinDate);
        mav.addObject("organization", organization);
        mav.addObject("users", users);
        mav.addObject("user", user);
        mav.setViewName("o/organization");
        return mav;
    }

}
