package org.opencloudengine.serviceportal.service;

import org.opencloudengine.serviceportal.db.entity.App;
import org.opencloudengine.serviceportal.db.mapper.AppMapper;
import org.opencloudengine.serviceportal.db.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    }

    public App getApp(String appId) {
        return appMapper.select(appId);
    }

    public void createApp(App app) {

        appMapper.insert(app);

    }



}
