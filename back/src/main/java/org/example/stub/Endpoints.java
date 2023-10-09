package org.example.stub;

import io.quarkus.vertx.web.Body;
import io.quarkus.vertx.web.Route;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Endpoints {
    private final ResponseTimeService service;

    public Endpoints(ResponseTimeService service) {
        this.service = service;
    }

    @Route(path = "/bound", methods = Route.HttpMethod.POST)
    public Bound setBound(@Body Bound request) {
        service.setResponseTime(request.getLower(), request.getUpper());
        return service.getBound();
    }

    @Route(path = "/bound", methods = Route.HttpMethod.GET)
    public Bound getBound() {
        return service.getBound();
    }
}
