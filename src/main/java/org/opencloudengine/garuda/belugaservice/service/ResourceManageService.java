package org.opencloudengine.garuda.belugaservice.service;

import org.opencloudengine.garuda.belugaservice.db.entity.Resource;
import org.opencloudengine.garuda.belugaservice.db.entity.Resource;
import org.opencloudengine.garuda.belugaservice.db.mapper.ResourceMapper;
import org.opencloudengine.garuda.belugaservice.util.ParseUtil;
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

    public List<Resource> getAllResources() {
        return resourceMapper.listAll();
    }

    public List<Resource> getOrgResources(String orgId) {
        return resourceMapper.listByOrganization(orgId);
    }

    public Resource getResource(String resourceId) {
        return resourceMapper.select(resourceId);
    }

    public void updateResource(Resource Resource) {
        resourceMapper.update(Resource);
    }

    public String updateResource(Map<String, Object> data) {
        Resource resource = parseResource(data);
        String id = (String) data.get("id");
        Resource oldResource = resourceMapper.select(id);
        oldResource.setName(resource.getName());
        oldResource.setHost(resource.getHost());
        oldResource.setPort(resource.getPort());
        resourceMapper.update(oldResource);
        return id;
    }

    public Resource parseResource(Map<String, Object> data) {
        String id = (String) data.get("id");
        String orgId = (String) data.get("orgId");
        String name = (String) data.get("name");
        String image = (String) data.get("image");
        String cpusStr = (String) data.get("cpus");
        float cpus = 0;
        if(cpusStr != null) {
            try {
                cpus = Float.parseFloat(cpusStr);
            } catch (Exception e) {
                //ignore
            }
        }
        String memoryStr = (String) data.get("memory");
        int memory = 0;
        if(memoryStr != null) {
            try {
                memory = Integer.parseInt(memoryStr);
            } catch (Exception e) {
                //ignore
            }
        }
        String host = (String) data.get("host");
        String port = (String) data.get("port");
        Resource resource = new Resource(id, orgId, name, image, cpus, memory);
        resource.setHost(host);
        resource.setPort(port);
        return resource;
    }

    public String createResource(Map<String, Object> data) {
        Resource resource = parseResource(data);
        String resourceUUID = UUID.randomUUID().toString();
        resource.setId(resourceUUID);
        resourceMapper.insert(resource);
        return resourceUUID;
    }

    public void createResource(Resource resource) {
        resourceMapper.insert(resource);
    }

    public void deleteApp(String resourceId) {
        resourceMapper.delete(resourceId);
    }
}
