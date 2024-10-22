package org.prashik.kafka.model;

import org.apache.kafka.common.Node;

import java.util.ArrayList;
import java.util.List;

public class Partition {
    private int partition;
    private String name;
    private Broker leader;

    private ArrayList<Broker> replicas;

    public Partition(int partition, String name, Node leader, List<Node> replicas) {
        this.partition = partition;
        this.name = name;
        this.leader = new Broker(leader);
        this.replicas = new ArrayList<>();
        for(Node replica: replicas) {
            Broker broker = new Broker(replica);
            this.replicas.add(broker);
        }
    }

    public int getPartition() {
        return partition;
    }

    public void setPartition(int partition) {
        this.partition = partition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Broker getLeader() {
        return leader;
    }

    public void setLeader(Broker leader) {
        this.leader = leader;
    }

    public ArrayList<Broker> getReplicas() {
        return replicas;
    }

    public void setReplicas(ArrayList<Broker> replicas) {
        this.replicas = replicas;
    }

    @Override
    public String toString() {
        String val = "Partition: " + this.partition + "\n"
                + "Name: " + this.name + "\n"
                + "Leader: " + this.leader.toString() + "\n"
                + "Replicas: " + this.replicas.toString() + "\n";
        return val;
    }
}
