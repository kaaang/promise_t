package kr.co.promise_t.api.kernel.infrastructure.cache;

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

    @Override
    public boolean exists(String key) {
        var exists = redisTemplate.hasKey(key);
        return exists != null && exists;
    }

    @Override
    public void expire(String key, Duration duration) {
        redisTemplate.expire(key, duration);
    }

    @Override
    public Long increase(String key, long increment) {
        return redisTemplate.opsForValue().increment(key, increment);
    }

    @Override
    public Long decrease(String key, long decrement) {
        return redisTemplate.opsForValue().decrement(key, decrement);
    }
}
