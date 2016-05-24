package org.opencloudengine.garuda.belugaservice.entity;

import org.opencloudengine.garuda.belugaservice.db.entity.App;

import java.util.List;

/**
 * Created by swsong on 2015. 8. 19..
 */
public class TerminalOpenRequest {

    private String image;
    private String container;
    private String host;

    public TerminalOpenRequest() {
    }

    public TerminalOpenRequest(String image, String container, String host) {
        this.image = image;
        this.container = container;
        this.host = host;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
