package org.prashik.kafka;

import org.apache.kafka.clients.admin.Admin;
import org.prashik.kafka.model.Topic;
import org.prashik.kafka.utils.ClusterUtilities;
import org.prashik.kafka.utils.TopicUtilities;

public class Main {
    public static void main(String[] args) {
        // let's hardcode this for now
        String kafkaServer = "0.0.0.0:9092";
        System.out.println("Kafka Server: " + kafkaServer + "\n");

        String topicName = "prashik-test";
        int partitions = 3;
        short replicationFactor = 1;

        // create admin client
        Admin admin = ClusterUtilities.createAdminClient(kafkaServer);

        // describe cluster
        System.out.println("Describe kafka cluster: ");
        ClusterUtilities.describeCluster(admin);

        // get all topics
        System.out.println("Create topic " + topicName);
        Topic topic = TopicUtilities.createTopic(admin, topicName, partitions, replicationFactor);

        System.out.println("Describe topic " + topicName);
        TopicUtilities.describeTopic(topic);

        // delete this topic
        System.out.println("Deleting Topic " + topicName);
        Topic topic1 = TopicUtilities.deleteTopic(admin, topicName);
        if(topic1 != null) {
            TopicUtilities.describeTopic(topic1);
        }
    }
}