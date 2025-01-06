package kr.co.promise_t.api.kernel.infrastructure.cache;

import java.time.Duration;

public interface KeyValueService {
    <E> void set(String key, E value);

    <E> void set(String key, E value, Duration duration);

    <E> E get(String key, Class<E> clazz);

    void delete(String key);

    boolean exists(String key);

    void expire(String key, Duration duration);

    Long increase(String key, long increment);

    Long decrease(String key, long decrement);
}
