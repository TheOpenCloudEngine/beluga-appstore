package org.opencloudengine.garuda.belugaservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.opencloudengine.garuda.belugaservice.db.entity.App;
import org.opencloudengine.garuda.belugaservice.db.entity.Resource;
import org.opencloudengine.garuda.belugaservice.entity.AppApplyRequest;
import org.opencloudengine.garuda.belugaservice.entity.AppStatus;
import org.opencloudengine.garuda.belugaservice.util.DateUtil;
import org.opencloudengine.garuda.belugaservice.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * Created by swsong on 2015. 8. 16..
 */

@Service
public class BelugaService {

    private static final Logger logger = LoggerFactory.getLogger(BelugaService.class);

    private static final String PROTOCOL = "http://";

    /**
     * Format : <ip>:<port>/<cluster id>
     * */
    @Value("#{systemProperties['beluga.endpoint']}")
    private String belugaEndPoint;

    private String hostId;
    private String clusterId;

    private String domainName;

    @PostConstruct
    public void init(){
        if(belugaEndPoint == null) {
            throw new IllegalArgumentException("Error : Please set system variable 'beluga.endpoint'.");
        }
        String[] els = belugaEndPoint.split("/");
        if(els.length < 2) {
            throw new IllegalArgumentException("Error : System variable 'beluga.endpoint' format is invalid. Format => ip:port/clusterId");
        }
        hostId = PROTOCOL + els[0].trim();
        clusterId = els[1].trim();
    }
    private WebTarget getWebTarget(String path) {
        Client client = ClientBuilder.newClient();
        return client.target(hostId).path(path);
    }

    public String getEndPoint() {
        return belugaEndPoint;
    }

    public String getDomainName() {
        if(domainName == null) {
            String uri = String.format("/v1/clusters/%s/domain", clusterId);
            domainName = getWebTarget(uri).request().get(String.class);
        }
        return domainName;
    }

    private JsonNode getBelugaResponse(String uri) {
        WebTarget webTarget = getWebTarget(uri);
        Response response = webTarget.request(MediaType.APPLICATION_JSON).get();
        try {
            if (response.getStatus() == 200) {
                String json = response.readEntity(String.class);
                JsonNode entity = JsonUtil.toJsonNode(json);
                return entity;
            } else {
                return null;
            }
        }catch (Throwable t) {
            logger.error("", t);
            return null;
        }
    }

    public String[] getAppHostPort(String appId) {
        return getAppHostPort(appId, null);
    }
    public String[] getAppHostPort(String appId, String resourceAppId) {
        String uri = null;
        if(resourceAppId == null) {
            uri = String.format("/v1/clusters/%s/apps/%s", clusterId, appId);
        } else {
            uri = String.format("/v1/clusters/%s/apps/%s", clusterId, appId + "/resources/" + resourceAppId);
        }
        JsonNode root = getBelugaResponse(uri);
        if(root == null || !root.has("app")) {
            //존재하지 않음.
            return null;
        }
        JsonNode app = root.get("app");
        ArrayNode tasks = (ArrayNode) app.get("tasks");
        if(tasks != null) {
            String host = "";
            String port = "";
            JsonNode task = tasks.get(0);
            if(task != null) {
                host = task.get("host").asText();
                ArrayNode ports = (ArrayNode) task.get("ports");
                if (ports.size() > 0) {
                    port = String.valueOf(ports.get(0).asInt());
                }
            }
            return new String[]{host, port};
        }

        return null;
    }

    public AppStatus getAppStatus(String appId) {
        String uri = String.format("/v1/clusters/%s/apps/%s", clusterId, appId);
        JsonNode root = getBelugaResponse(uri);
        if(root == null || !root.has("app")) {
            //존재하지 않음.
            return null;
        }
        JsonNode app = root.get("app");
        int instances = app.get("instances").asInt();
        int running = app.get("tasksRunning").asInt();
        int staged = app.get("tasksStaged").asInt();
        int totalRunning = running + staged;
        String dateString = app.get("version").asText();
        Date launchDate = DateUtil.getUtc2LocalTime(dateString);
        long elapseTime = DateUtil.getElapsedTime(launchDate);
        String elapseTimeDisplay = DateUtil.getElapsedTimeDisplay(elapseTime);

        String status = "-";
        String scale = "-";
        if(totalRunning == 0) {
            status = AppStatus.STATUS_OFF;
            scale = "0";
        } else {
            if(totalRunning == instances) {
                scale = String.valueOf(instances);
            } else {
                scale = totalRunning + " / " + instances;
            }
            if(running == instances && staged == 0) {
                status = AppStatus.STATUS_OK;
            } else {
                status = AppStatus.STATUS_SCALE;
            }
        }

        return new AppStatus(status, elapseTimeDisplay, scale);
    }

    public boolean updateApp(App app, boolean force) throws Exception {
        String appId = app.getId();
        String uri = String.format("/v1/clusters/%s/apps/%s", clusterId, appId);
        WebTarget webTarget = getWebTarget(uri);
        if(force) {
            webTarget = webTarget.queryParam("force", "true");
        }
        AppApplyRequest request = new AppApplyRequest(app);
        Response response = webTarget.request(MediaType.APPLICATION_JSON).put(Entity.json(request));
        if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
            return true;
        } else {
            String entity = response.readEntity(String.class);
            throw new Exception(entity);
        }
    }

    public boolean deployApp(App app) throws Exception {
        // garuda master 에 전송. marathon으로 실행.
        String appId = app.getId();
        String uri = String.format("/v1/clusters/%s/apps", clusterId);
        WebTarget webTarget = getWebTarget(uri);

        AppApplyRequest request = new AppApplyRequest(app);
        Response response = webTarget.request(MediaType.APPLICATION_JSON).post(Entity.json(request));
        if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
//            String str = response.readEntity(String.class);
//            Map<String, Object> entity = JsonUtil.json2Object(str);
//            logger.debug("Apply response : {}", entity);
            return true;
        } else {
            String entity = response.readEntity(String.class);
            throw new Exception(entity);
        }
    }

    public boolean deployResource(Resource resource) throws Exception {
        // garuda master 에 전송. marathon으로 실행.
        String uri = String.format("/v1/clusters/%s/resources", clusterId);
        WebTarget webTarget = getWebTarget(uri);
        Response response = webTarget.request(MediaType.APPLICATION_JSON).post(Entity.json(resource));
        if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
//            String str = response.readEntity(String.class);
//            Map<String, Object> entity = JsonUtil.json2Object(str);
//            logger.debug("Apply response : {}", entity);
            return true;
        } else {
            String entity = response.readEntity(String.class);
            throw new Exception(entity);
        }
    }

    public boolean destroyApp(String appId) throws IOException {
        String uri = String.format("/v1/clusters/%s/apps/%s", clusterId, appId);
        WebTarget webTarget = getWebTarget(uri);
        Response response = webTarget.request(MediaType.APPLICATION_JSON).delete();
        return response.getStatus() == 200;
    }

    public String uploadAppFile(String appId, File appFile) {

        /*
         * POST /v1/clusters/{clusterId}/apps/{appId}/file
         * param : file
         */
        String uri = String.format("/v1/clusters/%s/apps/%s/file", clusterId, appId);
        logger.debug("Upload file to garuda : {} > {}", appFile.getName(), uri);

        Client client = ClientBuilder.newClient().register(MultiPartFeature.class).register(JacksonFeature.class);
        WebTarget webTarget = client.target(hostId).path(uri);
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
