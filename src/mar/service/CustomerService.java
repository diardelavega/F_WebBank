package mar.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import comon.Transfer;

import entity.Customers;
import functions.CustomerFunctions;

@Path("/customer")
public class CustomerService {
	CustomerFunctions cf = new CustomerFunctions();

	@Path("/personal/{personalId}")
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces("text/html")
	public String checkPersonalId(@PathParam("personalId") String id) {
		// TODO check if personal id exists
		cf.checkPeraonalId(id);
		return null;
	}

	@Path("onlineRegistration")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("text/html")
	public String registrationApplication(Customers c) {
		// TODO check
		String validity = cf.dataValidity(c);
		if ("OK".equals(validity)) {
//			String confirmation = c
			return "";
		} else {
			return validity;
		}

	}

	@Path("/transfer")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void transfer(Transfer transfer) {
//		cf.transfer(transfer);
	}

	@Path("/info/{accId}")
	@POST
	@Produces("application/json")
	public void accountInfo(@PathParam("accId") String accId) {
		cf.info(accId);
	}

}
