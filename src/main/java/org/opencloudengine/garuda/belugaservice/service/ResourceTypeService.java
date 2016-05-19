package org.opencloudengine.garuda.belugaservice.service;

import org.opencloudengine.garuda.belugaservice.db.entity.ResourceType;
import org.opencloudengine.garuda.belugaservice.db.mapper.ResourceTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by swsong on 2015. 8. 17..
 */

@Service
public class ResourceTypeService {
    private static final Logger logger = LoggerFactory.getLogger(ResourceTypeService.class);

    @Autowired
    private ResourceTypeMapper resourceTypeMapper;

    public List<ResourceType> getAllResourceTypes() {
        return resourceTypeMapper.listAll();
    }

    public ResourceType getResourceType(String id) {
        return resourceTypeMapper.select(id);
    }

    public ResourceType parseResourceType(ResourceType resourceType) {
        MultipartFile uploadFile = resourceType.getUploadFile();

        try {
            if (uploadFile != null) {
                if(uploadFile.getBytes().length > 0){
                    resourceType.setFiletype(uploadFile.getContentType());
                    resourceType.setFile(uploadFile.getBytes());
                }
            }
        } catch (IOException ex) {
            logger.error("", ex);
        }
        return resourceType;
    }

    public String createResource(ResourceType resourceType) {
        resourceType = parseResourceType(resourceType);
        resourceTypeMapper.insert(resourceType);
        return resourceType.getId();
    }

    public String updateResourceType(ResourceType resourceType) {

        resourceType = parseResourceType(resourceType);
        ResourceType dbResouceType = resourceTypeMapper.select(resourceType.getId());

        if(resourceType.getFiletype() == null){
            resourceType.setFile(dbResouceType.getFile());
            resourceType.setFiletype(dbResouceType.getFiletype());
        }
        resourceTypeMapper.update(resourceType);
        return resourceType.getId();
    }

    public void deleteResource(String id) {
        resourceTypeMapper.delete(id);
    }
}
