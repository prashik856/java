package org.prashik.kafka.utils;

import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.DescribeClusterResult;
import org.prashik.kafka.model.KafkaCluster;

import java.util.Properties;

public class ClusterUtilities {
    public static Admin createAdminClient(String kafkaServer) {
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);

        Admin admin = null;
        try {
            admin = Admin.create(properties);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error creating kafka admin client.\n");
            System.exit(1);
        }
        System.out.println("Successfully created kafka admin client.\n");
        return admin;
    }

    public static void describeCluster(Admin admin) {
        try {
            DescribeClusterResult describeClusterResult = admin.describeCluster();
            KafkaCluster kafkaCluster = new KafkaCluster(describeClusterResult.clusterId().get(),
                    describeClusterResult.controller().get(), describeClusterResult.nodes().get());

            System.out.println("Cluster Information: ");
            System.out.println(kafkaCluster.toString());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error describing kafka cluster.\n");
            System.exit(1);
        }
    }
}
