package me.chat.test.api.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RedisTopicHelper {

    @Value("${spring.redis.topic-prefix")
    private String topicPrefix;

    public String topicAllKeys() {
        return topicPrefix + "-*";
    }

    public String specifiedTopicWithId(Number id) {
        return topicPrefix + "-" + id;
    }

}
