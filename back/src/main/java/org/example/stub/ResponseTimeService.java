package org.example.stub;

import jakarta.inject.Singleton;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

@Singleton
public class ResponseTimeService {
    private Bound bound;

    public ResponseTimeService(@ConfigProperty(name = "grpc-stub.bound.lower") long lower,
                               @ConfigProperty(name = "grpc-stub.bound.upper") long upper) {
        bound = new Bound(lower, upper);
    }

    public void setResponseTime(long lower, long upper) {
        bound = new Bound(lower, upper);
    }

    public Bound getBound() {
        return bound;
    }

    public Duration getResponseTime() {
        return Duration.ofMillis(
                ThreadLocalRandom.current().nextLong(
                        bound.getLower(), bound.getUpper()));
    }

}
