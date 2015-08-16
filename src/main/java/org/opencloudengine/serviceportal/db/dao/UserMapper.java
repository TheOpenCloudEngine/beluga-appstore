package org.opencloudengine.serviceportal.db.dao;

import org.apache.ibatis.annotations.Param;
import org.opencloudengine.serviceportal.db.entity.User;

import java.util.List;

public interface UserMapper {

	public List<User> selectAll();
	
	public User select(@Param("tenantId") String tenantId, @Param("id") String id, @Param("password") String password);

    public User selectById(@Param("tenantId") String tenantId, @Param("id") String id);

	public List<User> selectByTenant(@Param("tenantId") String tenantId);

	public void insert(User user);

    public void truncate();

}
