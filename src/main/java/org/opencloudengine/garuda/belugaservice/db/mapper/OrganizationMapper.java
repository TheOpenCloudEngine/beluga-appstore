package org.opencloudengine.garuda.belugaservice.db.mapper;

import org.apache.ibatis.annotations.Param;
import org.opencloudengine.garuda.belugaservice.db.entity.Organization;

public interface OrganizationMapper {

	public Organization select(@Param("id") String id);

	public void insert(Organization organization);

    public void truncate();

	public void delete(@Param("id") String id);
}
