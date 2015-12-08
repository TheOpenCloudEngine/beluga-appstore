package org.opencloudengine.garuda.belugaservice.db.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by swsong on 2015. 12. 8..
 */
public class Resource {
    private static final Logger logger = LoggerFactory.getLogger(Resource.class);

    private String id;
    private String orgId;
    private String appId;
    private String name;
    private String image;
    private float cpus;
    private float memory;
    private String host;
    private String port;
    private Date createDate;
    private String createDateDisplay;

    public Resource(String id, String orgId, String name, String image, float cpus, float mem) {
        this.id = id;
        this.orgId = orgId;
        this.name = name;
        this.image = image;
        this.cpus = cpus;
        this.memory = mem;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getCpus() {
        return cpus;
    }

    public void setCpus(float cpus) {
        this.cpus = cpus;
    }

    public float getMemory() {
        return memory;
    }

    public void setMemory(float memory) {
        this.memory = memory;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateDateDisplay() {
        return createDateDisplay;
    }

    public void setCreateDateDisplay(String createDateDisplay) {
        this.createDateDisplay = createDateDisplay;
    }

    public void fillCreateDateDisplay(DateFormat dateFormat) {
        this.createDateDisplay = dateFormat.format(createDate);
    }
}
