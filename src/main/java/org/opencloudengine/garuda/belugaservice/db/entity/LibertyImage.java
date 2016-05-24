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
public class LibertyImage {

    private static final Logger logger = LoggerFactory.getLogger(LibertyImage.class);
    private String id;
    private String tag;
    private String os;
    private String desc;
    private String image;
    private int port;
    private String cmd;
    private Date createDate;
    private String createDateDisplay;

    private String container;

    public LibertyImage() {
    }

    public LibertyImage(String id, String tag, String os, String desc, String image, int port, String cmd) {
        this.id = id;
        this.tag = tag;
        this.os = os;
        this.desc = desc;
        this.image = image;
        this.port = port;
        this.cmd = cmd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
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

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }
}
