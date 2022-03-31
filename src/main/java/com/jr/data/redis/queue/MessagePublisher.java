package com.jr.data.redis.queue;

public interface MessagePublisher {

    void publish(final String message);
}

