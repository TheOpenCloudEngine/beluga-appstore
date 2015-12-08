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
        Map<String, String> mysql5Env = new HashMap<>();
        mysql5Env.put("MYSQL_ROOT_PASSWORD", "1234");
        resourceProvidedList.add(new ResourceProvided("mysql5", "MySQL 5.7.9", "mysql:5.7.9", 3306, mysql5Env));
        Map<String, String> postgresql9Env = new HashMap<>();
        postgresql9Env.put("POSTGRES_PASSWORD", "1234");
        resourceProvidedList.add(new ResourceProvided("postgresql9", "PostgreSQL 9.4.5", "postgres:9.4.5", 5432, postgresql9Env));
        /*sid: xe
        username: system
        password: oracle*/
        resourceProvidedList.add(new ResourceProvided("oraclexe11g", "Oracle XE 11g", "wnameless/oracle-xe-11g", 1521, null));
        resourceProvidedList.add(new ResourceProvided("mongodb3", "MongoDB 3.0", "mongo:3.0", 27017, null));
        resourceProvidedList.add(new ResourceProvided("redis3", "Redis 3.0.5", "redis:3.0.5", 6379, null));
        Map<String, String> sftpEnv = new HashMap<>();
        sftpEnv.put("USER", "beluga");
        sftpEnv.put("PASS", "1234");
        resourceProvidedList.add(new ResourceProvided("sftp", "SFTP", "luzifer/sftp-share", 22, sftpEnv));
        Map<String, String> ftpEnv = new HashMap<>();
        ftpEnv.put("FTP_USER", "beluga");
        ftpEnv.put("FTP_PASS", "1234");
        ftpEnv.put("HOST", "localhost");
        resourceProvidedList.add(new ResourceProvided("ftp", "FTP", "mcreations/ftp", 21, ftpEnv));
    }

    private String id;
    private String name;
    private String image;
    private Integer port;
    private Map<String, String> env;

    public ResourceProvided(String appId, String appName, String appImage, Integer port, Map<String, String> env) {
        this.id = appId;
        this.name = appName;
        this.image = appImage;
        this.port = port;
        this.env = env;
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

    public Integer getPort() {
        return port;
    }

    public Map<String, String> getEnv() {
        return env;
    }
}
