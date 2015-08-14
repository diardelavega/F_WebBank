package mar.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;


@Path("/user")
public class UserRest {
	@GET
	public Response getUser(){
		return Response.status(200).entity("getUser is called").build();
	}

	@GET
	@Path("/vip")
	public Response getUserVIP() {
		 
		return Response.status(200).entity("getUserVIP is called").build();
 
	}
}
