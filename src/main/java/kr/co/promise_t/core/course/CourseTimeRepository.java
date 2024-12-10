package kr.co.promise_t.core.course;

import io.lettuce.core.dynamic.annotation.Param;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface CourseTimeRepository extends JpaRepository<CourseTime, UUID> {

    /**
     * 현재 조회량이 많이 않기 때문에 비관적 락을 사용하여서 동시성을 해결 TODO : SQS, Kafka, Redis 중 하나로 동시성을 해결하여 DB의 부하를 줄여야함
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select ct from CourseTime ct where ct.id=:id")
    Optional<CourseTime> findByIdWithLock(@Param("id") UUID id);
}
