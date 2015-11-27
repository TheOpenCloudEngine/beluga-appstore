package org.opencloudengine.garuda.belugaservice.entity;

import org.opencloudengine.garuda.belugaservice.db.entity.App;

import java.util.List;

/**
 * Created by swsong on 2015. 8. 19..
 */
public class AppApplyRequest {

    private String id;
    private String cmd;
    private Integer revision;
    private String context;
    private String file;
    private String context2;
    private String file2;
    private String type;
    private Float cpus;
    private Integer memory;
    private Integer scale;

    private List<String> resourceList;

    private Character autoScaleInUse;
    private App.AutoScaleConfig autoScaleInConfig;
    private Character autoScaleOutUse;
    private App.AutoScaleConfig autoScaleOutConfig;

    public AppApplyRequest() {}

    public AppApplyRequest(App app) {
        this.id = app.getId();
        this.revision = app.getAppFileRevision();
        this.context = app.getAppContext();
        this.file = app.getAppFilePath();
        this.context2 = app.getAppContext2();
        this.file2 = app.getAppFilePath2();
        this.type = app.getEnvironment();
        this.cpus = app.getCpus();
        this.memory = app.getMemory();
        this.scale = app.getScale();
        this.resourceList = app.getResourceList();
        autoScaleInUse = app.getAutoScaleInUse();
        autoScaleInConfig = app.getAutoScaleInConfig();
        autoScaleOutUse = app.getAutoScaleOutUse();
        autoScaleOutConfig = app.getAutoScaleOutConfig();
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

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getContext2() {
        return context2;
    }

    public void setContext2(String context2) {
        this.context2 = context2;
    }

    public String getFile2() {
        return file2;
    }

    public void setFile2(String file2) {
        this.file2 = file2;
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

    public List<String> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<String> resourceList) {
        this.resourceList = resourceList;
    }

    public Character getAutoScaleInUse() {
        return autoScaleInUse;
    }

    public void setAutoScaleInUse(Character autoScaleInUse) {
        this.autoScaleInUse = autoScaleInUse;
    }

    public App.AutoScaleConfig getAutoScaleInConfig() {
        return autoScaleInConfig;
    }

    public void setAutoScaleInConfig(App.AutoScaleConfig autoScaleInConfig) {
        this.autoScaleInConfig = autoScaleInConfig;
    }

    public Character getAutoScaleOutUse() {
        return autoScaleOutUse;
    }

    public void setAutoScaleOutUse(Character autoScaleOutUse) {
        this.autoScaleOutUse = autoScaleOutUse;
    }

    public App.AutoScaleConfig getAutoScaleOutConfig() {
        return autoScaleOutConfig;
    }

    public void setAutoScaleOutConfig(App.AutoScaleConfig autoScaleOutConfig) {
        this.autoScaleOutConfig = autoScaleOutConfig;
    }
}
