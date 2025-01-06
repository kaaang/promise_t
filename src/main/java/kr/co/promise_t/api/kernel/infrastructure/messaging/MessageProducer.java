package kr.co.promise_t.api.kernel.infrastructure.messaging;

public interface MessageProducer {
    void sendMessage(String topic, Object message);
}
