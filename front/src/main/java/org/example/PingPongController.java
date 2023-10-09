package org.example;

import io.quarkus.grpc.GrpcClient;
import io.quarkus.runtime.annotations.RegisterForReflection;
import io.smallrye.mutiny.Uni;
import org.example.proto.pingpong.Msg;
import org.example.proto.pingpong.PingPong;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@RegisterForReflection(targets = {
        MyClass.class,
})
@Path("/api/v1")
public class PingPongController {
    private final static Msg PING = Msg.newBuilder().setBody("ping").build();

    @GrpcClient
    PingPong pingPong;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/hello")
    public String hello() {
        return "Hello from App";
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/ping")
    public Uni<String> ping() {
        return pingPong.ping(PING)
                .onItem().transform(Msg::getBody);
    }
}