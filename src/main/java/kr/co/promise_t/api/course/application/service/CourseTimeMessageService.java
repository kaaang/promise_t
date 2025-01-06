package kr.co.promise_t.api.course.application.service;

import kr.co.promise_t.api.course.application.listener.event.CourseTimeReservedEvent;
import kr.co.promise_t.api.kernel.infrastructure.messaging.MessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseTimeMessageService {
    private final MessageProducer producer;

    @Value("${topic.course-time.reserved.topic}")
    private String courseTimeReservedTopic;

    public void sendCourseTimeReservedMessage(CourseTimeReservedEvent event) {
        producer.sendMessage(courseTimeReservedTopic, event);
    }
}
