package kr.co.promise_t.api.kernel.infrastructure;

import java.time.Duration;

public interface KeyValueService {
    <E> void set(String key, E value);

    <E> void set(String key, E value, Duration duration);

    <E> E get(String key, Class<E> clazz);

    void delete(String key);
}
