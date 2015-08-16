package org.opencloudengine.serviceportal.db.dao;

import org.apache.ibatis.annotations.Param;
import org.opencloudengine.serviceportal.db.entity.TenantUser;

import java.util.List;

public interface TenantUserMapper {
	public List<TenantUser> selectAll();
	
	public TenantUser select(@Param("tenantId") String tenantId, @Param("userId") String userId);

	public void insert(TenantUser tenantUser);

    public void truncate();
}
