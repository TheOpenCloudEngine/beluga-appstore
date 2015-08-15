package org.opencloudengine.console.web.db.entity;

/**
 * Created by swsong on 2015. 2. 2..
 */
public class Tenant {
    private String id;
    private String name;
    private String adminId;

    public Tenant(){
    }

    public Tenant(String id, String name, String adminId) {
        this.id = id;
        this.name = name;
        this.adminId = adminId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }
}
