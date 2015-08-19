package org.opencloudengine.serviceportal.service;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.opencloudengine.serviceportal.db.entity.App;
import org.opencloudengine.serviceportal.db.entity.Resources;
import org.opencloudengine.serviceportal.db.mapper.AppMapper;
import org.opencloudengine.serviceportal.entity.AppApplyRequest;
import org.opencloudengine.serviceportal.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by swsong on 2015. 8. 16..
 */

@Service
public class GarudaService {

    private static final Logger logger = LoggerFactory.getLogger(GarudaService.class);

    private static final String PROTOCOL = "http://";

    @Value("#{systemProperties['garauda.endpoint']}")
    private String garudaEndPoint;

    @Autowired
    private AppMapper appMapper;

    private WebTarget getWebTarget(String path) {
        Client client = ClientBuilder.newClient();
        return client.target(PROTOCOL + garudaEndPoint).path(path);
    }

    public String getEndPoint() {
        return garudaEndPoint;
    }

    public Resources getResources() {

        //TODO

        Resources resources = null;
        return resources;
    }

    public boolean applyApp(String clusterId, App app) throws IOException {
        // garuda master 에 전송. marathon으로 실행.
        String appId = app.getId();
        String uri = String.format("/v1/clusters/%s/apps", clusterId);
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(PROTOCOL + garudaEndPoint).path(uri);

        AppApplyRequest request = new AppApplyRequest(app);
        Response response = webTarget.request(MediaType.APPLICATION_JSON).post(Entity.json(request));
        if (response.getStatus() == 200) {
            String str = response.readEntity(String.class);
            Map<String, Object> entity = JsonUtil.json2Object(str);
            logger.debug("Apply response : {}", entity);
            return true;
        }
        return false;
    }

    public boolean destoryApp(String clusterId, String appId) throws IOException {
        String uri = String.format("/v1/clusters/%s/apps/%s", clusterId, appId);
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(PROTOCOL + garudaEndPoint).path(uri);
        Response response = webTarget.request(MediaType.APPLICATION_JSON).delete();
        return response.getStatus() == 200;
    }

    public String uploadAppFile(String clusterId, String appId, File appFile) {

        /*
         * POST /v1/clusters/{clusterId}/apps/{appId}/file
         * param : file
         */

        String uri = String.format("/v1/clusters/%s/apps/%s/file", clusterId, appId);
        logger.debug("Upload file to garuda : {} > {}", appFile.getName(), uri);
        Client client = ClientBuilder.newClient().register(MultiPartFeature.class).register(JacksonFeature.class);
        WebTarget webTarget = client.target(PROTOCOL + garudaEndPoint).path(uri);
        logger.debug("Upload URI : {}", webTarget.getUri());
        MultiPart multiPart = new MultiPart();
        multiPart.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);

        FileDataBodyPart fileDataBodyPart = new FileDataBodyPart("file",appFile, MediaType.APPLICATION_OCTET_STREAM_TYPE);
        multiPart.bodyPart(fileDataBodyPart);

        Response response = webTarget.request(MediaType.APPLICATION_JSON).post(Entity.entity(multiPart, multiPart.getMediaType()));

        try {
            if (response.getStatus() == 200) {
                String str = response.readEntity(String.class);
                Map<String, Object> entity = JsonUtil.json2Object(str);
                logger.debug("Upload response : {}", entity);
                return (String) entity.get("filePath");
            } else {
                return null;
            }
        }catch (Throwable t) {
            logger.error("", t);
            return null;
        }
    }
}
