package org.opencloudengine.serviceportal.db.entity;

/**
 * Created by swsong on 2015. 2. 2..
 */
public class TenantUser {
    private String tenantId;
    private String userId;

    public TenantUser(){
    }
    public TenantUser(String tenantId, String userId) {
        this.tenantId = tenantId;
        this.userId = userId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
