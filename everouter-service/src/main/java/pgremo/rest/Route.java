package pgremo.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("route")
public class Route {

    @GET
    @Produces(APPLICATION_JSON)
    public RouteResponse getIt() {
        return new RouteResponse();
    }
}