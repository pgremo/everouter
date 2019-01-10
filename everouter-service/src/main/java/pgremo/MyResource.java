package pgremo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("myresource")
public class MyResource {
    Logger logger = LoggerFactory.getLogger(MyResource.class);

    @GET
    @Produces(APPLICATION_JSON)
    public MyResourceResponse getIt() {
        logger.error("problem?");
        return new MyResourceResponse();
    }
}