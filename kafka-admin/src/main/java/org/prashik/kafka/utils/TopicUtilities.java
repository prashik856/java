package org.prashik.kafka.utils;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.TopicPartitionInfo;
import org.apache.kafka.common.config.TopicConfig;
import org.prashik.kafka.model.Partition;
import org.prashik.kafka.model.Topic;

import java.util.*;

public class TopicUtilities {
    public static Topic createTopic(Admin admin, String topicName, int partitions,
                                                 short replicationFactor) {
        Topic topic = getTopicInformation(admin, topicName);
        if(topic == null) {
            // create topic now
            CreateTopicsResult result = null;
            try {
                result = admin.createTopics(
                        Collections.singleton(
                                new NewTopic(topicName, partitions, replicationFactor)
                                        .configs(
                                                Collections.singletonMap(TopicConfig.CLEANUP_POLICY_CONFIG,
                                                        TopicConfig.CLEANUP_POLICY_COMPACT)
                                        )
                        )
                );
                KafkaFuture<Void> future = result.values().get(topicName);
                future.get();
                System.out.println("Successfully created topic: " + topicName + "\n");

                topic = getTopicInformation(admin, topicName);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error while creating topic " + topicName + ".\n");
                System.exit(1);
            }
        } else {
            System.out.println("Topic " + topicName + " already exists. Skipping topic creation.\n");
        }
        return topic;
    }

    public static ArrayList<TopicListing> getAllTopics(Admin admin) {
        ArrayList<TopicListing> topicListings = null;
        try {
            ListTopicsResult listTopicsResult =  admin.listTopics();

            topicListings = new ArrayList<>(listTopicsResult.listings().get());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error getting all the topics.\n");
            System.exit(1);
        }
        return topicListings;
    }

    public static Topic getTopicInformation(Admin admin, String topicName) {
        TopicListing resultantTopic = null;
        ArrayList<TopicListing> topicListings = getAllTopics(admin);
        for(TopicListing topicListing: topicListings) {
            if(Objects.equals(topicListing.name(), topicName)) {
                resultantTopic = topicListing;
                break;
            }
        }

        if(resultantTopic == null) {
            return null;
        }
        Topic kafkaTopic = null;
        Collection<String> topics = new ArrayList<>();
        topics.add(resultantTopic.name());

        try {
            DescribeTopicsResult describeTopicsResult = admin.describeTopics(topics);
            Map<String, TopicDescription> topicDescriptionMap = describeTopicsResult.allTopicNames().get();
            for(var entry: topicDescriptionMap.entrySet()) {
                ArrayList<Partition> partitions = new ArrayList<>();
                List<TopicPartitionInfo> topicPartitionInfoList = entry.getValue().partitions();
                for(TopicPartitionInfo topicPartitionInfo: topicPartitionInfoList) {
                    partitions.add(
                            new Partition(topicPartitionInfo.partition(),
                                    String.valueOf(topicPartitionInfo.partition()),
                                    topicPartitionInfo.leader(),
                                    topicPartitionInfo.replicas())
                    );
                }
                kafkaTopic = new Topic(entry.getValue().topicId(),
                        entry.getValue().isInternal(),
                        entry.getValue().name(),
                        partitions);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error while describing the given topic.");
            System.exit(1);
        }

        return kafkaTopic;
    }

    public static void describeTopic(Admin admin, String topicName) {
        Topic topic = getTopicInformation(admin, topicName);
        printTopicInfo(topic);
    }

    public static void describeTopic(Topic topic) {
        printTopicInfo(topic);
    }

    public static void printTopicInfo(Topic topic) {
        System.out.println("Topic Information: ");
        System.out.println(topic.toString());
    }
}
