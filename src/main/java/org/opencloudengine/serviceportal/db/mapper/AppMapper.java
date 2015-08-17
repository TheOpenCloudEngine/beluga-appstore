package org.opencloudengine.serviceportal.db.mapper;

import org.apache.ibatis.annotations.Param;
import org.opencloudengine.serviceportal.db.entity.App;

import java.util.List;

/**
 * Created by swsong on 2015. 8. 17..
 */
public interface AppMapper {

    public List<App> listAll();

    public List<App> listByOrganization(@Param("orgId") String orgId);

    public App select(@Param("id") String id);

    public void insert(App app);

    public void update(App app);

    public void delete(@Param("id") String id);

    public void truncate();

    public void addGrant(@Param("orgId") String orgId, @Param("appId") String appId);

    public Integer getGrant(@Param("orgId") String orgId, @Param("appId") String appId);
}
