package org.opencloudengine.garuda.belugaservice.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 제공하는 리소스의 모음.
 * Created by swsong on 2015. 12. 8..
 */
public class ResourceProvided {

    private static final Logger logger = LoggerFactory.getLogger(ResourceProvided.class);
    public static List<ResourceProvided> resourceProvidedList = new ArrayList<>();
    static {
        resourceProvidedList.add(new ResourceProvided("mysql5", "MySQL 5.7.9", "mysql:5.7.9"));
        resourceProvidedList.add(new ResourceProvided("postgresql9", "PostgreSQL 9.4.5", "postgres:9.4.5"));
        resourceProvidedList.add(new ResourceProvided("oraclexe11g", "Oracle XE 11g", "wnameless/oracle-xe-11g"));
        resourceProvidedList.add(new ResourceProvided("mongodb3", "MongoDB 3.0", "mongo:3.0"));
        resourceProvidedList.add(new ResourceProvided("redis3", "Redis 3.0.5", "redis:3.0.5"));
        resourceProvidedList.add(new ResourceProvided("sftp", "SFTP", "luzifer/sftp-share"));
        resourceProvidedList.add(new ResourceProvided("ftp", "FTP", "mcreations/ftp"));
    }

    private String id;
    private String name;
    private String image;

    public ResourceProvided(String appId, String appName, String appImage) {
        this.id = appId;
        this.name = appName;
        this.image = appImage;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
