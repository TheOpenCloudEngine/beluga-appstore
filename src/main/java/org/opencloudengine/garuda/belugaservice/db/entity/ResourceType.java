package org.opencloudengine.garuda.belugaservice.db.entity;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONStringer;
import org.codehaus.jettison.json.JSONWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by swsong on 2015. 12. 8..
 */
public class ResourceType {
    private static final Logger logger = LoggerFactory.getLogger(ResourceType.class);
    private String id;
    private String liberty;
    private String catalog;
    private String name;
    private String image;
    private int port;
    private Map<String, String> envMap;
    private String env;
    private String desc;
    private Date createDate;
    private String createDateDisplay;
    private byte[] file;
    private String filetype;
    private MultipartFile uploadFile;

    public ResourceType() {
    }

    public ResourceType(String id, String liberty, String catalog, String name, String image, int port, String env, String desc, byte[] file, String filetype) {
        this.id = id;
        this.liberty = liberty;
        this.catalog = catalog;
        this.name = name;
        this.image = image;
        this.port = port;
        this.env = env;
        this.desc = desc;
        this.file = file;
        this.filetype = filetype;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLiberty() {
        return liberty;
    }

    public void setLiberty(String liberty) {
        this.liberty = liberty;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
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

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public Map<String, String> getEnvMap() {
        return envMap;
    }

    public void setEnvMap(Map<String, String> envMap) {
        this.envMap = envMap;
    }

    public String getEnv() {

        if (env != null) {
            return env;
        }
        JSONStringer json = new JSONStringer();
        if (envMap != null) {
            try {
                JSONWriter w = json.object();
                for (Map.Entry<String, String> e : envMap.entrySet()) {
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
        if (env != null) {
            this.envMap = new HashMap<>();
            try {
                JSONObject obj = new JSONObject(env);
                Iterator<String> iter = obj.keys();
                while (iter.hasNext()) {
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

    public MultipartFile getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(MultipartFile uploadFile) {
        this.uploadFile = uploadFile;
    }
}
