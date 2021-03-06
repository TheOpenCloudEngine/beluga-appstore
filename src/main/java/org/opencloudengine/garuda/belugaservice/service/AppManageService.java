package org.opencloudengine.garuda.belugaservice.service;

import org.opencloudengine.garuda.belugaservice.db.entity.App;
import org.opencloudengine.garuda.belugaservice.db.mapper.AppMapper;
import org.opencloudengine.garuda.belugaservice.util.ParseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by swsong on 2015. 8. 17..
 */

@Service
public class AppManageService {
    private static final Logger logger = LoggerFactory.getLogger(AppManageService.class);

    private static final String APP_UPLOAD_ROOT = "/tmp/garuda/upload";
    @Autowired
    private AppMapper appMapper;

    public List<App> getAllApps() {
        return appMapper.listAll();
    }

    public List<App> getOrgApps(String orgId) {
        return appMapper.listByOrganization(orgId);
    }

    public List<App> getAppsById(String appId) {
        return appMapper.listByAppId(appId);
    }

    public App getApp(String appId, Integer version) {
        return appMapper.select(appId, version);
    }

    public void updateApp(App app) {
        appMapper.update(app);
    }

    public String updateApp(Map<String, Object> data) {
        App app = parseApp(data);
        String id = (String) data.get("id");
        Integer version = (Integer) data.get("version");
        App oldApp = appMapper.select(id, version);

        /*
        * 1. file이 바뀌었는지 체크한다. checksum비교.
        * */
        if (isEquals(oldApp.getAppContext(), app.getAppContext())
                && isEquals(oldApp.getAppContext2(), app.getAppContext2())
                && isEquals(oldApp.getAppFileChecksum(), app.getAppFileChecksum())
                && isEquals(oldApp.getAppFileChecksum2(), app.getAppFileChecksum2())
                && isEquals(oldApp.getEnvironment(), app.getEnvironment())
                ) {
            //같으면
            //중요: deploy를 안하고 여러번 수정했을 경우, 중간에 appFile을 바꿨으면 그대로 바꾼상태로 놔둔다.
            // 그렇지 않으면, 중간에 바꼈음에도 불구하고, 마지막에 updated가 N이 되면, 도커를 교체하지 않는 버그가 생기게 된다.
            app.setAppFileUpdated(oldApp.getAppFileUpdated());
        } else {
            //달라졌으면
            app.setAppFileUpdated(App.CHECK_YES);
        }
        appMapper.update(app);
        return app.getId();
    }

    private boolean isEquals(String a, String b) {
        if (a == null && b == null) {
            return true;
        }
        if (a != null && b != null) {
            return a.equals(b);
        }
        return false;
    }

    public App parseApp(Map<String, Object> data) {
        String id = (String) data.get("id");
        Integer version = (Integer) data.get("version");
        String orgId = (String) data.get("orgId");
        String name = (String) data.get("name");
        String description = (String) data.get("description");

        String appContext = (String) data.get("context1");
        String appFile = (String) data.get("fileName1");
        String appFilePath = (String) data.get("filePath1");
        String appFileLength = (String) data.get("fileLength1");
        String appFileDate = (String) data.get("fileDate1");
        String appFileChecksum = (String) data.get("fileChecksum1");

        String appContext2 = (String) data.get("context2");
        String appFile2 = (String) data.get("fileName2");
        String appFilePath2 = (String) data.get("filePath2");
        String appFileLength2 = (String) data.get("fileLength2");
        String appFileDate2 = (String) data.get("fileDate2");
        String appFileChecksum2 = (String) data.get("fileChecksum2");
        if (appFile2.length() == 0 || appFile2.length() == 0) {
            appContext2 = null;
        }

        String envs = (String) data.get("envs");

        String environment = (String) data.get("environment");
        String cpus = (String) data.get("cpus");
        String memory = (String) data.get("memory");
        String scale = (String) data.get("scale");

        String autoScaleUse = (String) data.get("autoScaleUse");
        Integer scaleOutLoad = ParseUtil.parseInt(data.get("scaleOutLoad"));
        Integer scaleOutTimeInMin = ParseUtil.parseInt(data.get("scaleOutTimeInMin"));
        Integer scaleInLoad = ParseUtil.parseInt(data.get("scaleInLoad"));
        Integer scaleInTimeInMin = ParseUtil.parseInt(data.get("scaleInTimeInMin"));

        App app = new App();
        app.setId(id);
        app.setOrgId(orgId);
        app.setVersion(version);
        app.setName(name);
        app.setDescription(description);
        //app file1
        app.setAppContext(appContext);
        app.setAppFile(appFile);
        app.setAppFilePath(appFilePath);
        app.setAppFileLength(ParseUtil.parseLong(appFileLength));
        app.setAppFileDate(appFileDate);
        app.setAppFileChecksum(appFileChecksum);
        //app file2
        if (appContext2 != null) {
            app.setAppContext2(appContext2);
            app.setAppFile2(appFile2);
            app.setAppFilePath2(appFilePath2);
            app.setAppFileLength2(ParseUtil.parseLong(appFileLength2));
            app.setAppFileDate2(appFileDate2);
            app.setAppFileChecksum2(appFileChecksum2);
        }
        //environment
        app.setEnvironment(environment);
        app.setCpus(ParseUtil.parseFloat(cpus));
        app.setMemory(ParseUtil.parseInt(memory));
        app.setScale(ParseUtil.parseInt(scale));

        /* environment plan */
        app.setEnvs(envs);

        /* resources plan */
        List<String> resourceList = new ArrayList<>();
        for (String key : data.keySet()) {
            if (key.startsWith("res_")) {
                String resourceId = (String) data.get(key);
                resourceList.add(resourceId);
            }
        }
        app.setResourcesPlan(resourceList);

        /* auto scale */
        App.AutoScaleConfig autoScaleConfig = new App.AutoScaleConfig((autoScaleUse != null), scaleOutLoad, scaleOutTimeInMin, scaleInLoad, scaleInTimeInMin);
        app.setAutoScaleConfig(autoScaleConfig);

        return app;
    }

    public String createApp(Map<String, Object> data) {
        data.put("version", 1);
        App app = parseApp(data);
        app.setCurrentUse(App.CURRENT_YES);
        appMapper.insert(app);
        return app.getId();
    }

    public String createVersion(Map<String, Object> data) {
        String id = (String) data.get("id");
        App lastVersionApp = this.selectMaxVersion(id);
        Integer version = lastVersionApp.getVersion() + 1;

        data.put("version", version);
        App app = parseApp(data);
        app.setCurrentUse(App.CURRENT_NO);
        appMapper.insert(app);
        return app.getId();
    }

    public void deleteApp(String appId) {
        appMapper.delete(appId);
    }

    public void deleteAppVersion(String appId, Integer version) {
        appMapper.deleteVersion(appId, version);
    }

    public File saveMultipartFile(MultipartFile file, String orgId) throws IOException {

        if (file == null || file.isEmpty()) {
            return null;
        }
        File dir = new File(APP_UPLOAD_ROOT, orgId);
        dir.mkdirs();

        String fileName = file.getOriginalFilename();
        fileName = URLDecoder.decode(fileName, "utf-8");

        File f = new File(dir, fileName);
        logger.debug("App uploaded >> {}", f.getAbsolutePath());
        InputStream is = file.getInputStream();
        BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(f));
        try {
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) >= 0) {
                os.write(buffer, 0, len);
            }
        } finally {
            os.close();
            is.close();
        }
        return f;
    }

    public void setAppFileUpdatedDone(String appId, Integer version) {
        appMapper.setAppFileUpdatedDone(appId, version);
    }

    public void setAppUse(String appId, Integer version) {
        appMapper.setAppUse(appId, version);
    }

    public void setAppNotUse(String appId) {
        appMapper.setAppNotUse(appId);
    }

    public App selectMaxVersion(String appId) {
        return appMapper.selectMaxVersion(appId);
    }
}
