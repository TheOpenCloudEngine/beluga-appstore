package org.opencloudengine.garuda.belugaservice.db.mapper;

import org.apache.ibatis.annotations.Param;
import org.opencloudengine.garuda.belugaservice.db.entity.Resource;
import org.opencloudengine.garuda.belugaservice.db.entity.ResourceType;

import java.util.List;

/**
 * Created by swsong on 2015. 8. 17..
 */
public interface ResourceTypeMapper {

    public List<ResourceType> listAll();

    public ResourceType select(@Param("id") String id);

    public void insert(ResourceType resourceType);

    public void update(ResourceType resourceType);

    public void delete(@Param("id") String id);

    public void truncate();
}
