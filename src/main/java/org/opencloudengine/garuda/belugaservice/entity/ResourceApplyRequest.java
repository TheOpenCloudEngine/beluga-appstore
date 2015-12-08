package org.opencloudengine.garuda.belugaservice.entity;

import org.opencloudengine.garuda.belugaservice.db.entity.App;
import org.opencloudengine.garuda.belugaservice.db.entity.Resource;

import java.util.List;

/**
 * Created by swsong on 2015. 8. 19..
 */
public class ResourceApplyRequest {

    private String id;
    private Float cpus;
    private Integer memory;

    public ResourceApplyRequest() {}

    public ResourceApplyRequest(Resource resource) {
        this.id = resource.getId();
        this.cpus = resource.getCpus();
        this.memory = resource.getMemory();
    }


}
