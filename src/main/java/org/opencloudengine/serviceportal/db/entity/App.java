package org.opencloudengine.serviceportal.db.entity;

import org.opencloudengine.serviceportal.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created by swsong on 2015. 8. 17..
 */
public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    /* General Information */
    private String id;
    private String orgId;
    private String name;
    private String description;

    /* Operating Plan */
    private String appFile;
    private String appFileDate;
    private Long appFileSize;
    private char appFileUpdated;
    private Float cpus;
    private Integer memory;
    private Float scale;
    private String version;

    /* Resource Plan */
    private ResourcesPlan resourcesPlan;

    /* Auto Scaling Plan */
    private char autoScaleOutUse;
    private char autoScaleInUse;
    private AutoScaleOutConfig autoScaleOutConf;
    private AutoScaleInConfig autoScaleInConf;

    /* Apply state */
    private char applied;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAppFile() {
        return appFile;
    }

    public void setAppFile(String appFile) {
        this.appFile = appFile;
    }

    public String getAppFileDate() {
        return appFileDate;
    }

    public void setAppFileDate(String appFileDate) {
        this.appFileDate = appFileDate;
    }

    public Long getAppFileSize() {
        return appFileSize;
    }

    public void setAppFileSize(Long appFileSize) {
        this.appFileSize = appFileSize;
    }

    public char getAppFileUpdated() {
        return appFileUpdated;
    }

    public void setAppFileUpdated(char appFileUpdated) {
        this.appFileUpdated = appFileUpdated;
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

    public Float getScale() {
        return scale;
    }

    public void setScale(Float scale) {
        this.scale = scale;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public ResourcesPlan getResourcesPlan() {
        return resourcesPlan;
    }

    public void setResourcesPlan(String resources) {
        try{
            this.resourcesPlan = JsonUtil.json2Object(resources, ResourcesPlan.class);
        } catch (IOException e) {
            logger.error("", e);
        }
    }

    public char getAutoScaleOutUse() {
        return autoScaleOutUse;
    }

    public void setAutoScaleOutUse(char autoScaleOutUse) {
        this.autoScaleOutUse = autoScaleOutUse;
    }

    public char getAutoScaleInUse() {
        return autoScaleInUse;
    }

    public void setAutoScaleInUse(char autoScaleInUse) {
        this.autoScaleInUse = autoScaleInUse;
    }

    public AutoScaleOutConfig getAutoScaleOutConfig() {
        return autoScaleOutConf;
    }

    public void setAutoScaleOutConf(String autoScaleOutConf) {
        try{
           this.autoScaleOutConf = JsonUtil.json2Object(autoScaleOutConf, AutoScaleOutConfig.class);
        } catch (IOException e) {
            logger.error("", e);
        }
    }

    public AutoScaleInConfig getAutoScaleInConfig() {
        return autoScaleInConf;
    }

    public void setAutoScaleInConf(String autoScaleInConf) {
        try {
            this.autoScaleInConf = JsonUtil.json2Object(autoScaleInConf, AutoScaleInConfig.class);
        } catch (IOException e) {
            logger.error("", e);
        }
    }

    public char getApplied() {
        return applied;
    }

    public void setApplied(char applied) {
        this.applied = applied;
    }

    public static class ResourcesPlan {

        private List<ResourcePlan> planList;

        public List<ResourcePlan> getPlanList() {
            return planList;
        }

        public void setPlanList(List<ResourcePlan> planList) {
            this.planList = planList;
        }

    }

    public static class AutoScaleOutConfig {
        private int cpuHigher;
        private int cpuHigherDuring;
        private int addScale;

        public int getCpuHigher() {
            return cpuHigher;
        }

        public void setCpuHigher(int cpuHigher) {
            this.cpuHigher = cpuHigher;
        }

        public int getCpuHigherDuring() {
            return cpuHigherDuring;
        }

        public void setCpuHigherDuring(int cpuHigherDuring) {
            this.cpuHigherDuring = cpuHigherDuring;
        }

        public int getAddScale() {
            return addScale;
        }

        public void setAddScale(int addScale) {
            this.addScale = addScale;
        }

    }

    public static class AutoScaleInConfig {

        private int cpuLower;
        private int cpuLowerDuring;

        public int getCpuLower() {
            return cpuLower;
        }

        public void setCpuLower(int cpuLower) {
            this.cpuLower = cpuLower;
        }

        public int getCpuLowerDuring() {
            return cpuLowerDuring;
        }

        public void setCpuLowerDuring(int cpuLowerDuring) {
            this.cpuLowerDuring = cpuLowerDuring;
        }

    }
}
