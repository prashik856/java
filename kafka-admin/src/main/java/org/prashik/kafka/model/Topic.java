package org.prashik.kafka.model;

import org.apache.kafka.common.Uuid;

import java.util.ArrayList;

public class Topic {
    private Uuid uuid;
    private boolean isInternal;
    private String name;
    private ArrayList<Partition> partitions;

    public Topic(Uuid uuid, boolean isInternal, String name, ArrayList<Partition> partitions) {
        this.uuid = uuid;
        this.isInternal = isInternal;
        this.name = name;
        this.partitions = partitions;
    }

    public String toString() {
        String val = "Name: " + this.name + "\n"
                + "UUID: " + this.uuid.toString() + "\n"
                + "isInternal: " + this.isInternal + "\n"
                + partitions.toString() + "\n";
        return val;
    }
}
