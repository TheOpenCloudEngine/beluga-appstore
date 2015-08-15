package org.opencloudengine.console.web.db.entity;

import java.io.Serializable;

public class User implements Serializable{

	private static final long serialVersionUID = 1L;
    public static final String ADMIN = "A";

	private String id;
	
	private String name;
	
	private String password;
	
	private String tenantId;

	private boolean isAdmin;

    public User(){
    }

    public User(String id, String name, String password, String tenantId) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.tenantId = tenantId;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password
				+ ", tenantId=" + tenantId + ", "+ isAdmin +"]";
	}
}
