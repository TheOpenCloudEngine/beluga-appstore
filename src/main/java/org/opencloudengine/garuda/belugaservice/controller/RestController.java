package org.opencloudengine.garuda.belugaservice.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.opencloudengine.garuda.belugaservice.db.entity.*;
import org.opencloudengine.garuda.belugaservice.entity.AppStatus;
import org.opencloudengine.garuda.belugaservice.service.*;
import org.opencloudengine.garuda.belugaservice.util.DateUtil;
import org.opencloudengine.garuda.belugaservice.util.JsonUtil;
import org.opencloudengine.garuda.belugaservice.util.MessageDigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

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
    ResourceManageService resourceManageService;

    @Autowired
    ResourceTypeService resourceTypeService;

    @Autowired
    LibertyImageService imageService;

    @Autowired
    private BelugaService belugaService;

    @RequestMapping(value = "/api/apps/{appId}/{version}", method = RequestMethod.HEAD)
    public void apps(@PathVariable String appId, @PathVariable Integer version, HttpServletResponse response) throws IOException {
        App app = appManageService.getApp(appId, version);
        if (app != null) {
            response.setStatus(200);
        } else {
            response.setStatus(404, "no such app : " + appId);
        }
    }

    @RequestMapping(value = "/api/resources/{resourceId}", method = RequestMethod.HEAD)
    public void resource(@PathVariable String resourceId, HttpServletResponse response) throws IOException {
        Resource resource = resourceManageService.getResource(resourceId);
        if (resource != null) {
            response.setStatus(200);
        } else {
            response.setStatus(404, "no such resource : " + resource);
        }
    }

    @RequestMapping(value = "/api/resourcetype/{id}", method = RequestMethod.HEAD)
    public void resourcetype(@PathVariable String id, HttpServletResponse response) throws IOException {
        ResourceType resourcType = resourceTypeService.getResourceType(id);
        if (resourcType != null) {
            response.setStatus(200);
        } else {
            response.setStatus(404, "no such resource : " + resourcType);
        }
    }

    @RequestMapping(value = "/api/resourcetype/{id}/image", method = RequestMethod.GET)
    public ResponseEntity<byte[]> resourcetypeImage(@PathVariable String id, HttpServletResponse response) throws IOException {
        ResourceType resourcType = resourceTypeService.getResourceType(id);

        if (resourcType != null) {
            byte[] imageContent = resourcType.getFile();
            final HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", resourcType.getFiletype());
            return new ResponseEntity<byte[]>(imageContent, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<byte[]>(null, null, HttpStatus.NO_CONTENT);
        }
    }

    @RequestMapping(value = "/api/resourcetype/{id}", method = RequestMethod.DELETE)
    public void deleteResourceType(@PathVariable String id, HttpServletResponse response) throws IOException {
        ResourceType resourceType = resourceTypeService.getResourceType(id);

        if (resourceType != null) {
            resourceTypeService.deleteResource(id);
            response.setStatus(200);
            return;
        } else {
            response.sendError(500, "no such resource type: " + id);
        }
    }

    @RequestMapping(value = "/api/apps/{appId}/{version}/status", method = RequestMethod.GET)
    public void appStatus(@PathVariable String appId, @PathVariable Integer version, HttpServletResponse response) throws IOException {
        AppStatus appStatus = null;
        App app = appManageService.getApp(appId, version);
        if (app.getCurrentUse().equals(App.CURRENT_NO)) {
            appStatus = new AppStatus("-", "-", "-");
        } else {
            appStatus = belugaService.getAppStatus(appId);
            if (appStatus == null) {
                appStatus = new AppStatus("-", "-", "-");
            }
        }
        response.setStatus(200);
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(JsonUtil.object2String(appStatus));
    }

    @RequestMapping(value = "/api/resources/{resourceId}/status", method = RequestMethod.GET)
    public void resourcesStatus(@PathVariable String resourceId, HttpServletResponse response) throws IOException {
        AppStatus appStatus = belugaService.getAppStatus(resourceId);
        if (appStatus == null) {
            appStatus = new AppStatus("-", "-", "-");
        }
        response.setStatus(200);
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(JsonUtil.object2String(appStatus));
    }

    @RequestMapping(value = "/api/apps/upload", method = RequestMethod.POST)
    public void uploadAppFile(@RequestParam("file") MultipartFile file, HttpServletResponse response, HttpSession session) throws IOException {
        User user = (User) session.getAttribute(User.USER_KEY);
        String orgId = user.getOrgId();
        File appFile = appManageService.saveMultipartFile(file, orgId);

        if (appFile != null) {
            // garuda에 올린다.
            String filePath = belugaService.uploadAppFile(orgId, appFile);
            if (filePath != null) {
                long length = appFile.length();
                String fileName = appFile.getName();
                String checksum = MessageDigestUtils.getMD5Checksum(appFile);
                UploadFile uploadFile = new UploadFile(fileName, filePath, length, checksum, DateUtil.getNow());
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

    @RequestMapping(value = "/api/apps/{appId}/{version}/deploy", method = RequestMethod.POST)
    public void appDeploy(@PathVariable String appId, @PathVariable Integer version, HttpServletResponse response) throws Exception {

        try {
            App app = appManageService.getApp(appId, version);
            if (app != null) {
                // 이미 실행중인 marathon app이 있는지 확인한다.
                AppStatus appStatus = belugaService.getAppStatus(appId);
                boolean isSuccess = false;
                if (appStatus == null) {
                    //신규실행.
                    isSuccess = belugaService.deployApp(app);
                } else {
                    //업데이트 실행.
                    Character isAppFileUpdated = app.getAppFileUpdated();
                    if (isAppFileUpdated != null && isAppFileUpdated.charValue() == App.CHECK_NO) {
                        //app 이 업데이트 되지 않았다면,
                        app.setAppContext(null);
                        app.setAppContext2(null);
                    }
                    isSuccess = belugaService.updateApp(app, true);
                }
                if (isSuccess) {
                    // 앱파일을 적용했으므로, 변경되지 않음으로 갱신한다.
                    appManageService.setAppFileUpdatedDone(appId, version);

                    //해당 버젼을 커런트 버젼으로 변경한다.
                    appManageService.setAppNotUse(appId);
                    appManageService.setAppUse(appId, version);
                    response.setStatus(200);
                    return;
                } else {
                    response.sendError(500, "error : " + appId);
                }
            } else {
                response.sendError(404, "no such app : " + appId);
            }
        } catch (Exception e) {
            response.setStatus(500);
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(e.getMessage());
        }
    }

    @RequestMapping(value = "/api/apps/{appId}/{version}/deploystop/{cmd}", method = RequestMethod.POST)
    public void appDeployStop(@PathVariable String appId, @PathVariable Integer version, @PathVariable String cmd, HttpServletResponse response) throws Exception {
        try {
            App app = appManageService.getApp(appId, version);
            if (app != null) {
                boolean success = belugaService.deployStopApp(app, cmd);
                if (success) {
                    response.setStatus(200);
                } else {
                    response.sendError(500, "error : " + appId);
                }
            } else {
                response.sendError(404, "no such app : " + appId);
            }
        } catch (Exception e) {
            response.setStatus(500);
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(e.getMessage());
        }
    }

    @RequestMapping(value = "/api/resources/{resourceId}/deploy", method = RequestMethod.POST)
    public void resourceDeploy(@PathVariable String resourceId, HttpServletResponse response) throws Exception {

        try {
            Resource resource = resourceManageService.getResource(resourceId);
            if (resource != null) {
                // 이미 실행중인 marathon app이 있는지 확인한다.
                AppStatus appStatus = belugaService.getAppStatus(resourceId);
                boolean isSuccess = false;
                if (appStatus == null) {
                    //신규실행.
                    isSuccess = belugaService.deployResource(resource);
                } else {
                    //업데이트 실행.
                    //현재 업데이트는 지원하지 않는다.
                }
                if (isSuccess) {
                    response.setStatus(200);
                    return;
                } else {
                    response.sendError(500, "error : " + resourceId);
                }
            } else {
                response.sendError(404, "no such resource : " + resourceId);
            }
        } catch (Exception e) {
            response.setStatus(500);
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(e.getMessage());
        }
    }

    @RequestMapping(value = "/api/apps/{appId}/{version}/scale/{scale}", method = RequestMethod.POST)
    public void appScale(@PathVariable String appId, @PathVariable Integer version, @PathVariable String scale, HttpServletResponse response) throws Exception {

        try {
            int scaleInt = Integer.parseInt(scale);
            App app = new App();
            app.setId(appId);
            app.setScale(scaleInt);
            App dbApp = appManageService.getApp(appId, version);
            if (app != null) {
                /*
                 * 변경되었거나 꼭 필요한 항목만 넣어준다.
                 */
                //리소스와 환경변수를 머지하지 위해 넣어준다.
                app.setEnvs(dbApp.getEnvs());

                //리소스를 넣어주어야 손실이 방지된다.
                app.setResources(dbApp.getResources());
                if (belugaService.updateApp(app, true)) {
                    dbApp.setScale(scaleInt);
                    appManageService.updateApp(dbApp);
                    response.setStatus(200);
                    return;
                } else {
                    response.sendError(500, "error : " + appId);
                }
            } else {
                response.sendError(404, "no such app : " + appId);
            }
        } catch (Exception e) {
            response.setStatus(500);
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(e.getMessage());
        }
    }

    @RequestMapping(value = "/api/apps/{appId}", method = RequestMethod.DELETE)
    public void deleteApp(@PathVariable String appId, HttpServletResponse response) throws IOException {
        if (belugaService.destroyApp(appId)) {
            appManageService.deleteApp(appId);
            response.setStatus(200);
            return;
        } else {
            response.sendError(500, "no such app : " + appId);
        }
    }

    @RequestMapping(value = "/api/apps/{appId}/{version}", method = RequestMethod.DELETE)
    public void deleteAppVersion(@PathVariable String appId, @PathVariable Integer version, HttpServletResponse response) throws IOException {
        App app = appManageService.getApp(appId, version);
        if (app != null) {
            if (app.getCurrentUse().equals(App.CURRENT_YES)) {
                response.sendError(500, "unable to delete current deploy app version: " + appId + "/" + version);
            } else {
                appManageService.deleteAppVersion(appId, version);
                response.setStatus(200);
                return;
            }
        } else {
            response.sendError(500, "no such app version: " + appId + "/" + version);
        }
    }

    @RequestMapping(value = "/api/resources/{appId}", method = RequestMethod.DELETE)
    public void deleteResource(@PathVariable String appId, HttpServletResponse response) throws IOException {
        if (belugaService.destroyApp(appId)) {
            resourceManageService.deleteResource(appId);
            response.setStatus(200);
            return;
        } else {
            response.sendError(500, "no such resource : " + appId);
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

    @RequestMapping(value = "/api/image/boot", method = RequestMethod.POST)
    public void imageBoot(@RequestBody Map<String, Object> data, HttpServletResponse response, HttpSession session) throws IOException {
        String image = data.get("os").toString();
        String container = UUID.randomUUID().toString();

        JsonNode jsonNode = belugaService.bootTerminal(image, container);
        System.out.println(jsonNode);

        if (jsonNode.has("image")) {
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(jsonNode.toString());
        } else {
            response.sendError(500, "Cannot open terminal : " + image);
        }
    }

    @RequestMapping(value = "/api/image/{id}/{tag}/boot", method = RequestMethod.POST)
    public void imageBootContinue(@PathVariable String id, @PathVariable String tag, @RequestBody Map<String, Object> data, HttpServletResponse response, HttpSession session) throws IOException {

        LibertyImage libertyImage = imageService.getLibertyImage(id, tag);
        String container = UUID.randomUUID().toString();

        JsonNode jsonNode = belugaService.bootTerminal(libertyImage.getImage(), container);

        if (jsonNode.has("image")) {
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(jsonNode.toString());
        } else {
            response.sendError(500, "Cannot open terminal : " + libertyImage.getImage());
        }
    }

    @RequestMapping(value = "/api/image/{id}/{tag}/new", method = RequestMethod.POST)
    public void imageBootNew(@PathVariable String id, @PathVariable String tag, @RequestBody Map<String, Object> data, HttpServletResponse response, HttpSession session) throws IOException {

        String image = data.get("os").toString();
        String container = UUID.randomUUID().toString();

        JsonNode jsonNode = belugaService.bootTerminal(image, container);
        System.out.println(jsonNode);

        if (jsonNode.has("image")) {
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(jsonNode.toString());
        } else {
            response.sendError(500, "Cannot open terminal : " + image);
        }
    }

    @RequestMapping(value = "/api/image/{id}/{tag}", method = RequestMethod.DELETE)
    public void deleteImage(@PathVariable String id, @PathVariable String tag, HttpServletResponse response) throws IOException {
        LibertyImage libertyImage = imageService.getLibertyImage(id, tag);

        if (libertyImage != null) {
            imageService.deleteLibertyImage(id, tag);
            response.setStatus(200);
        } else {
            response.sendError(500, "no such image : " + id + ":" + tag);
        }
    }
}
