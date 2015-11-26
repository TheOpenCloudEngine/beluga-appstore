package org.opencloudengine.garuda.belugaservice.db.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by swsong on 2015. 11. 26..
 */
public class Resources {

    public static Map<String, Resource> resourceMap = new HashMap<>();
    static {
        resourceMap.put("mysql5", new Resource("mysql5", "MySQL 5.7.9", "mysql:5.7.9", 0.2f, 500f));
        resourceMap.put("postgresql9", new Resource("postgresql9", "PostgreSQL 9.4.5", "postgres:9.4.5", 0.2f, 500f));
        resourceMap.put("oraclexe11g", new Resource("oraclexe11g", "Oracle XE 11g", "wnameless/oracle-xe-11g", 0.2f, 1000f));
        resourceMap.put("mongodb3", new Resource("mongodb3", "MongoDB 3.0", "mongodb:3.0", 0.2f, 500f));
        resourceMap.put("redis3", new Resource("redis3", "Redis 3.0.5", "redis:3.0.5", 0.2f, 500f));
        resourceMap.put("sftp", new Resource("sftp", "SFTP", "luzifer/sftp-share", 0.1f, 200f));
        resourceMap.put("ftp", new Resource("ftp", "FTP", "mcreations/ftp", 0.1f, 200f));
    }

    public static Set<String> keys() {
        return resourceMap.keySet();
    }

    public static Resource get(String key) {
        return resourceMap.get(key);
    }

    public static class Resource {
        private String id;
        private String name;
        private String image;
        private float cpus;
        private float mem;

        public Resource(String appId, String appName, String appImage, float cpus, float mem) {
            this.id = appId;
            this.name = appName;
            this.image = appImage;
            this.cpus = cpus;
            this.mem = mem;
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

        public float getCpus() {
            return cpus;
        }

        public float getMem() {
            return mem;
        }

        public String getIPPropertyKey() {
            return "BELUGA_" + id.toUpperCase() + "_IP";
        }

        public String getPortPropertyKey() {
            return "BELUGA_" + id.toUpperCase() + "_PORT";
        }
    }
}
