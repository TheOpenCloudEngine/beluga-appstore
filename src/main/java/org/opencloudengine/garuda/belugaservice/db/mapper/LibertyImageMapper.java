package org.opencloudengine.garuda.belugaservice.db.mapper;

import org.apache.ibatis.annotations.Param;
import org.opencloudengine.garuda.belugaservice.db.entity.LibertyImage;
import org.opencloudengine.garuda.belugaservice.db.entity.ResourceType;

import java.util.List;

/**
 * Created by swsong on 2015. 8. 17..
 */
public interface LibertyImageMapper {

    public List<LibertyImage> listAll();

    public LibertyImage select(@Param("id") String id, @Param("tag") String tag);

    public void insert(LibertyImage libertyImage);

    public void update(LibertyImage libertyImage);

    public void delete(@Param("id") String id, @Param("tag") String tag);

    public void truncate();
}
