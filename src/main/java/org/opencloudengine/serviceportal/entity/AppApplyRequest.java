package org.opencloudengine.serviceportal.entity;

import org.opencloudengine.serviceportal.db.entity.App;

/**
 * Created by swsong on 2015. 8. 19..
 */
public class AppApplyRequest {
    private String id;
    private String cmd;
    private Integer port;
    private String file;
    private String type;
    private Float cpus;
    private Integer memory;
    private Integer scale;

    public AppApplyRequest() {}

    public AppApplyRequest(App app) {
        this.id = app.getId();
        this.port = app.getEnvironment().startsWith("java") ? 8080 : 80; //WAS 면 8080을 연다.
        this.file = app.getAppFilePath();
        this.type = app.getEnvironment();
        this.cpus = app.getCpus();
        this.memory = app.getMemory();
        this.scale = app.getScale();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getCpus() {
        return cpus;
    }

    public void setCpus(Float cpus) {
        this.cpus = cpus;
    }

    public Integer getMemory() {
        return memory;
    }

    public void setMemory(Integer memory) {
        this.memory = memory;
    }

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }
}
