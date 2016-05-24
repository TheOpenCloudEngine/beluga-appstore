package org.opencloudengine.garuda.belugaservice.entity;

/**
 * Created by swsong on 2015. 8. 19..
 */
public class TerminalCommitRequest {

    private String image;
    private String container;
    private String cmd;
    private int port;

    public TerminalCommitRequest() {
    }

    public TerminalCommitRequest(String image, String container, String cmd, int port) {
        this.image = image;
        this.container = container;
        this.cmd = cmd;
        this.port = port;
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

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
