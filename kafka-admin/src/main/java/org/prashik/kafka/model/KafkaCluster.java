package org.prashik.kafka.model;

import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.Node;

import java.util.Arrays;
import java.util.Collection;

public class KafkaCluster {
    private final String clusterName;
    private final Node controller;
    private final Collection<Node> nodes;

    public KafkaCluster(String clusterName, Node controller, Collection<Node> nodes) {
        this.clusterName = clusterName;
        this.controller = controller;
        this.nodes = nodes;
    }

    public String getClusterName() {
        return clusterName;
    }

    public Node getController() {
        return controller;
    }

    public Collection<Node> getNodes() {
        return nodes;
    }

    @Override
    public String toString() {
        String val = "ID: " + this.clusterName + "\n"
                + "Controller Host: " + this.controller.host() + "\n"
                + "Controller ID: " + this.controller.id() + "\n"
                + "Controller Port: " + this.controller.port() + "\n"
                + "Nodes: " + Arrays.toString(this.nodes.toArray()) + "\n";
        return val;
    }
}
