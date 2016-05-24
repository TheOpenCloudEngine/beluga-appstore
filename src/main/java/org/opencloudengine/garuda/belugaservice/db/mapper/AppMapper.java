package org.opencloudengine.garuda.belugaservice.db.mapper;

import org.apache.ibatis.annotations.Param;
import org.opencloudengine.garuda.belugaservice.db.entity.App;

import java.util.List;

/**
 * Created by swsong on 2015. 8. 17..
 */
public interface AppMapper {

    public List<App> listAll();

    public List<App> listByOrganization(@Param("orgId") String orgId);

    public List<App> listByAppId(@Param("id") String id);

    public App select(@Param("id") String id, @Param("version") Integer version);

    public void insert(App app);

    public void update(App app);

    public void delete(@Param("id") String id);

    public void deleteVersion(@Param("id") String id, @Param("version") Integer version);

    public void truncate();

    public void setAppFileUpdatedDone(@Param("id") String id, @Param("version") Integer version);

    public void setAppUse(@Param("id") String id, @Param("version") Integer version);

    public void setAppNotUse(@Param("id") String id);

    public App selectMaxVersion(@Param("id") String id);
}
