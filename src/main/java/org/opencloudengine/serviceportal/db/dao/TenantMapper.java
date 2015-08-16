package org.opencloudengine.serviceportal.db.dao;

import org.apache.ibatis.annotations.Param;
import org.opencloudengine.serviceportal.db.entity.Organization;

import java.util.List;

public interface TenantMapper {
	public List<Organization> selectAll();
	
	public Organization select(@Param("id") String id);
	
	public void insert(Organization organization);

    public void truncate();

}
