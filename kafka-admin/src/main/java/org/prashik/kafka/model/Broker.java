package org.prashik.kafka.model;

import org.apache.kafka.common.Node;

public class Broker {
    private String host;
    private String id;
    private String port;

    public Broker(Node node) {
        this.host = node.host();
        this.id = node.idString();
        this.port = String.valueOf(node.port());
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Override
    public String toString() {
        String val = "Node id: " + this.id + "\n"
                + "Node host: " + this.host + "\n"
                + "Node port: " + this.port;
        return val;
    }
}
