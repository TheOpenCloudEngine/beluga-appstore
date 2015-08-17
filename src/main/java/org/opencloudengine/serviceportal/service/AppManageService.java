package org.opencloudengine.serviceportal.service;

import org.opencloudengine.serviceportal.db.entity.App;
import org.opencloudengine.serviceportal.db.entity.ResourcePlan;
import org.opencloudengine.serviceportal.db.entity.Resources;
import org.opencloudengine.serviceportal.db.mapper.AppMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;

/**
 * Created by swsong on 2015. 8. 17..
 */

@Service
public class AppManageService {
    private static final Logger logger = LoggerFactory.getLogger(AppManageService.class);

    private static final String APP_UPLOAD_ROOT = "/tmp/garuda/upload";
    @Autowired
    private AppMapper appMapper;

    public List<App> getAppsByOrganization(String orgId) {
        return appMapper.listByOrganization(orgId);
    }

    public void updateApp(App app) {
        appMapper.update(app);
    }

    public App getApp(String appId) {
        return appMapper.select(appId);
    }

    public void createApp(App app) {
        appMapper.insert(app);

    }

    public void addGrant(String orgId, String appId) {
        appMapper.addGrant(orgId, appId);
    }

    public boolean isGranted(String orgId, String appId) {
        return appMapper.getGrant(orgId, appId) > 0;
    }

    public Resources getUsingResources(String appId, Resources allResources) {
        Resources usingResources = allResources;
        App app = getApp(appId);

        App.ResourcesPlan resourcesPlan = app.getResourcesPlan();

        /* DB Plan Check */
        Iterator<Resources.DBResource> dbIter = allResources.getDbResourceList().iterator();
        while(dbIter.hasNext()) {
            String resourceId = dbIter.next().getId();
            if(!isPlanContainsResource(resourcesPlan, resourceId)) {
                //plan에 속하지 않는 resource는 삭제한다.
                dbIter.remove();
            }
        }

        /* DB Plan Check */
        Iterator<Resources.FTPResource> ftpIter = allResources.getFtpResourceList().iterator();
        while(ftpIter.hasNext()) {
            String resourceId = ftpIter.next().getId();
            if(!isPlanContainsResource(resourcesPlan, resourceId)) {
                //plan에 속하지 않는 resource는 삭제한다.
                ftpIter.remove();
            }
        }

        return usingResources;
    }

    private boolean isPlanContainsResource(App.ResourcesPlan resourcesPlan, String resourceId) {
        for(ResourcePlan plan : resourcesPlan.getPlanList()) {
            plan.getId().equalsIgnoreCase(resourceId);
            return true;
        }
        return false;
    }

    public File uploadAppFile(MultipartFile file, String orgId) throws IOException {

        if(file == null || file.isEmpty()) {
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
}
