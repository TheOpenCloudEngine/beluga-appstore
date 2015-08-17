package org.opencloudengine.serviceportal.db.entity;

/**
 * Created by swsong on 2015. 8. 17..
 */
public class App {

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
    private String resources;

    /* Auto Scaling Plan */
    private char autoScaleOutUse;
    private char autoScaleInUse;
    private String autoScaleOutConf;
    private String autoScaleInConf;

    /* Apply state */
    private char applied;


}
