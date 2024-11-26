package kr.co.promise_t.api.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;

@Configuration
@Profile("test")
public class TestRedisConfig {

    /**
     * TODO : 컨테이너 실행 위치를 바꿀 수 있는지 확인
     * 현재의 클래스는 config 클래스 이기 때문에 컨테이너 실행에 문제가 존재한다.
     * 하지만 테스트 컨테이너를 따로 두었을 때, 컨텍스트들이 먼저 로드 되면서 redis 를 먼저 yml에 있는 프로퍼티로 연결을 시도한다.
     * @DynamicPropertySource 를 시도하여서 SpringBoot 컨텍스트가 초기화 전에 값을 바꿀려고 하였지만 지속적으로 테스트 컨테이너가 떠서 값을
     * 변경하기 전에 초기화가 일어나 컨테이너 연결을 하지 못하였음.
     * 일시적으로 테스트 정상화를 위하여 해당 클래스로 컨테이너 실행을 이관.
     * 해당 문제는 해결하면 기록을 꼭 남겨두어야 한다.
     */
    @Container
    private static final GenericContainer<?> REDIS = new GenericContainer<>("redis:6").withExposedPorts(6379).withReuse(true);

    static {
        REDIS.start();
    }

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        var host = REDIS.getHost();
        var port = REDIS.getMappedPort(6379);
        return new LettuceConnectionFactory(host, port);
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.registerModule(new JavaTimeModule());

        return mapper;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(
            LettuceConnectionFactory connectionFactory, ObjectMapper objectMapper) {
        var template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(connectionFactory);

        var stringSerializer = new StringRedisSerializer();
        var jsonSerializer = new GenericJackson2JsonRedisSerializer(objectMapper);

        template.setKeySerializer(stringSerializer);
        template.setValueSerializer(jsonSerializer);
        template.setHashKeySerializer(stringSerializer);
        template.setHashValueSerializer(jsonSerializer);

        return template;
    }
}
