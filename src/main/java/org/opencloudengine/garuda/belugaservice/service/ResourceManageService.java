package org.opencloudengine.garuda.belugaservice.service;

import org.opencloudengine.garuda.belugaservice.db.entity.Resource;
import org.opencloudengine.garuda.belugaservice.db.entity.ResourceType;
import org.opencloudengine.garuda.belugaservice.db.mapper.ResourceMapper;
import org.opencloudengine.garuda.belugaservice.db.mapper.ResourceTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by swsong on 2015. 8. 17..
 */

@Service
public class ResourceManageService {
    private static final Logger logger = LoggerFactory.getLogger(ResourceManageService.class);

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private ResourceTypeMapper resourceTypeMapper;

    public List<Resource> getAllResources() {
        return resourceMapper.listAll();
    }

    public List<Resource> getOrgResources(String orgId) {
        return resourceMapper.listByOrganization(orgId);
    }

    public Resource getResource(String resourceId) {
        return resourceMapper.select(resourceId);
    }

    public Resource parseResource(Map<String, Object> data) {
        String id = (String) data.get("id");
        String orgId = (String) data.get("orgId");
        String name = (String) data.get("name");
        String type = (String) data.get("type");
//        int port = 0;
//        String resourceName = null;
//        String image = null;
//        Map<String, String> env = null;
//        for (ResourceProvided r : ResourceProvided.resourceProvidedList) {
//            if (r.getId().equalsIgnoreCase(type)) {
//                port = r.getPort();
//                resourceName = r.getName();
//                image = r.getImage();
//                env = r.getEnv();
//                break;
//            }
//        }
        ResourceType resourceType = resourceTypeMapper.select(type);
        int port = resourceType.getPort();
        String resourceName = resourceType.getName();
        String image = resourceType.getImage();
        Map<String, String> env = resourceType.getEnvMap();

        String cpusStr = (String) data.get("cpus");
        float cpus = 0;
        if (cpusStr != null) {
            try {
                cpus = Float.parseFloat(cpusStr);
            } catch (Exception e) {
                //ignore
            }
        }
        String memoryStr = (String) data.get("memory");
        int memory = 0;
        if (memoryStr != null) {
            try {
                memory = Integer.parseInt(memoryStr);
            } catch (Exception e) {
                //ignore
            }
        }

        Resource resource = new Resource(id, orgId, name, resourceName, image, port, env, cpus, memory);
        return resource;
    }

    public String createResource(Map<String, Object> data) {
        Resource resource = parseResource(data);
        resourceMapper.insert(resource);
        return resource.getId();
    }

    public void deleteResource(String resourceId) {
        resourceMapper.delete(resourceId);
    }
}
