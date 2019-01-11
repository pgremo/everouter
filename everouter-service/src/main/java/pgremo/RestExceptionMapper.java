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
        return Response.status(getStatus(exception))
                .entity(getEntity(exception))
                .build();
    }

    private Response.Status getStatus(Throwable exception) {
        if (exception instanceof WebApplicationException) {
            return ((WebApplicationException) exception).getResponse().getStatusInfo().toEnum();
        }
        return INTERNAL_SERVER_ERROR;
    }

    private Object getEntity(Throwable exception) {
        StringWriter message = new StringWriter();
        exception.printStackTrace(new PrintWriter(message));
        return message.toString();
    }
}
