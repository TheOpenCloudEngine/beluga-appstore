package org.opencloudengine.serviceportal.service;

import org.opencloudengine.serviceportal.db.entity.App;
import org.opencloudengine.serviceportal.db.entity.Resources;
import org.opencloudengine.serviceportal.db.mapper.AppMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by swsong on 2015. 8. 16..
 */

@Service
public class GarudaService {

    private static final Logger logger = LoggerFactory.getLogger(GarudaService.class);

    private static final String PROTOCOL = "http://";

    @Value("#{systemProperties['GARUDA_ENDPOINT']}")
    private String garudaEndPoint;

    @Autowired
    private AppMapper appMapper;

    public String getEndPoint() {
        return garudaEndPoint;
    }

    public Resources getResources() {

        //TODO

        Resources resources = null;
        return resources;
    }

    public void applyApp(String appId) {

        //TODO
        App app = appMapper.select(appId);


        //TODO garuda master 에 전송. marathon으로 실행.


    }
}
