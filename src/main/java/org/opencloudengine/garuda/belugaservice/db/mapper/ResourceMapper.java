package org.opencloudengine.garuda.belugaservice.db.mapper;

import org.apache.ibatis.annotations.Param;
import org.opencloudengine.garuda.belugaservice.db.entity.App;
import org.opencloudengine.garuda.belugaservice.db.entity.Resource;

import java.util.List;

/**
 * Created by swsong on 2015. 8. 17..
 */
public interface ResourceMapper {

    public List<Resource> listAll();

    public List<Resource> listByOrganization(@Param("orgId") String orgId);

    public Resource select(@Param("id") String id);

    public void insert(Resource resource);

    public void delete(@Param("id") String id);

    public void truncate();
}
