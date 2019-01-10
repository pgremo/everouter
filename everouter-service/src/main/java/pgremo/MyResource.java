package pgremo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("myresource")
public class MyResource {

    @GET
    @Produces(TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }
}