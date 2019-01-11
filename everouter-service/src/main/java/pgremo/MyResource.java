package pgremo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("myresource")
public class MyResource {

    @GET
    @Produces(APPLICATION_JSON)
    public MyResourceResponse getIt() {
        return new MyResourceResponse();
    }
}