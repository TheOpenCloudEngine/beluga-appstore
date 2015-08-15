package org.opencloudengine.console.web.db.dao;

import org.apache.ibatis.annotations.Param;
import org.opencloudengine.console.web.db.entity.Tenant;

import java.util.List;

public interface TenantMapper {
	public List<Tenant> selectAll();
	
	public Tenant select(@Param("id") String id);
	
	public void insert(Tenant tenant);

    public void truncate();

}
