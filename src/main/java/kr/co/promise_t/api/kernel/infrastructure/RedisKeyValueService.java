package kr.co.promise_t.api.kernel.infrastructure;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisKeyValueService implements KeyValueService {
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public <E> void set(String key, E value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public <E> void set(String key, E value, Duration duration) {
        redisTemplate.opsForValue().set(key, value, duration);
    }

    @Override
    public <E> E get(String key, Class<E> clazz) {
        return (E) redisTemplate.opsForValue().get(key);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
