package org.opencloudengine.garuda.belugaservice.service;

import org.opencloudengine.garuda.belugaservice.db.entity.LibertyImage;
import org.opencloudengine.garuda.belugaservice.db.entity.ResourceType;
import org.opencloudengine.garuda.belugaservice.db.mapper.LibertyImageMapper;
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
public class LibertyImageService {
    private static final Logger logger = LoggerFactory.getLogger(LibertyImageService.class);

    @Autowired
    private LibertyImageMapper libertyImageMapper;

    @Autowired
    private BelugaService belugaService;

    public List<LibertyImage> getAllLibertyImages() {
        return libertyImageMapper.listAll();
    }

    public LibertyImage getLibertyImage(String id, String tag) {
        return libertyImageMapper.select(id, tag);
    }

    public LibertyImage parseLibertyImage(LibertyImage libertyImage) {
        return libertyImage;
    }

    public LibertyImage createLibertyImage(LibertyImage libertyImage) {
        libertyImage = parseLibertyImage(libertyImage);

        String image = libertyImage.getId() + ":" + libertyImage.getTag();
        String container = libertyImage.getContainer();
        String cmd = libertyImage.getCmd();
        int port = libertyImage.getPort();

        String imageName = belugaService.commitTerminal(image, container, cmd, port);
        libertyImage.setImage(imageName);

        libertyImageMapper.insert(libertyImage);
        return libertyImage;
    }

    public LibertyImage updateLibertyImage(LibertyImage libertyImage) {

        libertyImage = parseLibertyImage(libertyImage);

        String image = libertyImage.getId() + ":" + libertyImage.getTag();
        String container = libertyImage.getContainer();
        String cmd = libertyImage.getCmd();
        int port = libertyImage.getPort();

        String imageName = belugaService.commitTerminal(image, container, cmd, port);
        libertyImage.setImage(imageName);

        libertyImageMapper.update(libertyImage);
        return libertyImage;
    }

    public void deleteLibertyImage(String id, String tag) {
        libertyImageMapper.delete(id, tag);
    }
}
