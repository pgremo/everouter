package pgremo;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.io.PrintWriter;
import java.io.StringWriter;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

@Provider
public class RestExceptionMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable exception) {
        return Response.status(getStatusCode(exception))
                .entity(getEntity(exception))
                .build();
    }

    /*
     * Get appropriate HTTP status code for an exception.
     */
    private Response.Status getStatusCode(Throwable exception) {
        if (exception instanceof WebApplicationException) {
            return ((WebApplicationException) exception).getResponse().getStatusInfo().toEnum();
        }

        return INTERNAL_SERVER_ERROR;
    }

    /*
     * Get response body for an exception.
     */
    private Object getEntity(Throwable exception) {
        StringWriter message = new StringWriter();
        exception.printStackTrace(new PrintWriter(message));
        return message.toString();
    }
}
