package org.opencloudengine.serviceportal.db.mapper;

import org.apache.ibatis.annotations.Param;
import org.opencloudengine.serviceportal.db.entity.User;

public interface UserMapper {

	public User select(@Param("id") String id);

    public User selectWithPassword(User user);

	public void insert(User user);

    public void truncate();

}