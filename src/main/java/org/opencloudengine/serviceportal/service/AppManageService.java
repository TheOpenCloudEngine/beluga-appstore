package org.opencloudengine.serviceportal.service;

import org.opencloudengine.serviceportal.db.entity.App;
import org.opencloudengine.serviceportal.db.entity.ResourcePlan;
import org.opencloudengine.serviceportal.db.entity.Resources;
import org.opencloudengine.serviceportal.db.mapper.AppMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * Created by swsong on 2015. 8. 17..
 */

@Service
public class AppManageService {

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
}
