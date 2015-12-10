package org.opencloudengine.garuda.belugaservice.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONStringer;
import org.codehaus.jettison.json.JSONWriter;
import org.opencloudengine.garuda.belugaservice.entity.AppStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by swsong on 2015. 12. 8..
 */
public class Resource {
    private static final Logger logger = LoggerFactory.getLogger(Resource.class);
    private String id;
    private String orgId;
    private String name;
    private String resourceName;
    private String image;
    private Integer port;
    private Map<String, String> envMap;
    private String env;
    private Float cpus;
    private Integer memory;
    private Date createDate;
    private String createDateDisplay;
    private String memoryDisplay;

    @JsonIgnoreProperties
    private boolean inUse; //리소스가 사용중인지 여부 플래그. 저장하지 않고 화면에 뿌릴때 임시사용.
    @JsonIgnoreProperties
    private String appStatus;

    public Resource() {}

    public Resource(String id, String orgId, String name, String resourceName, String image, Integer port, Map<String, String> envMap, Float cpus, Integer mem) {
        this.id = id;
        this.orgId = orgId;
        this.name = name;
        this.resourceName = resourceName;
        this.image = image;
        this.port = port;
        this.envMap = envMap;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public void setMemoryDisplay(String memoryDisplay) {
        this.memoryDisplay = memoryDisplay;
    }

    public String getMemoryDisplay() {
        return memoryDisplay;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Map<String, String> getEnvMap() {
        return envMap;
    }

    public void setEnvMap(Map<String, String> envMap) {
        this.envMap = envMap;
    }

    public String getEnv() {

        if(env != null) {
            return env;
        }
        JSONStringer json = new JSONStringer();
        if(envMap != null) {
            try {
                JSONWriter w = json.object();
                for(Map.Entry<String, String> e : envMap.entrySet()) {
                    w.key(e.getKey()).value(e.getValue());
                }
                w.endObject();
                this.env = w.toString();
            } catch (JSONException e) {
                logger.error("", e);
            }
        }
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
        if(env != null) {
            this.envMap = new HashMap<>();
            try {
                JSONObject obj = new JSONObject(env);
                Iterator<String> iter = obj.keys();
                while(iter.hasNext()) {
                    String key = iter.next();
                    this.envMap.put(key, obj.getString(key));
                }
            } catch (JSONException e) {
                logger.error("", e);
            }
        }

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

    public boolean isInUse() {
        return inUse;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

    public String getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(String appStatus) {
        this.appStatus = appStatus;
    }
}
