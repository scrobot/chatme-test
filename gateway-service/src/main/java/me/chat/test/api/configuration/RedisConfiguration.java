package me.chat.test.api.configuration;

import lombok.val;
import me.chat.test.api.data.models.Task;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {

    @Primary
    @Bean
    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory(
        @Value("${spring.redis.host}") String host,
        @Value("${spring.redis.port}") int port
    ) {
        return new LettuceConnectionFactory(host, port);
    }

    @Bean
    public ReactiveRedisTemplate<String, Task> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        val keySerializer = new StringRedisSerializer();
        val valueSerializer = new Jackson2JsonRedisSerializer<>(Task.class);
        val builder = RedisSerializationContext.<String, Task>newSerializationContext(keySerializer);
        val context = builder.value(valueSerializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }

}
