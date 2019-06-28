package tbc.server;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class Main {

    private static List<String> ips = ObjStore.getInstance();

    @GET
    @Path("/connections")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOpenConnections() {
        return Response.ok(ips.toArray()).build();
    }

    @POST
    @Path("/connections")
    @Produces(MediaType.APPLICATION_JSON)
    public Response postOpenConnection(
            @Context org.glassfish.grizzly.http.server.Request ctx
    ) {
        try {
            String address = ctx.getRemoteHost();
            ips.add(address);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return Response.ok().build();
    }

}
