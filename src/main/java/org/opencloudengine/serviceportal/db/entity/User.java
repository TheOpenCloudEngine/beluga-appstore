package org.opencloudengine.serviceportal.db.entity;

import java.io.Serializable;

public class User implements Serializable{

	private static final long serialVersionUID = 1L;
    public static final String ADMIN = "A";

	private String id;
	
	private String name;
	
	private String password;
	
	private String orgId;

	private boolean isAdmin;

    public User(){
    }

    public User(String id, String name, String password, String orgId) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.orgId = orgId;
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

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
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
				+ ", orgId=" + orgId + ", "+ isAdmin +"]";
	}
}
