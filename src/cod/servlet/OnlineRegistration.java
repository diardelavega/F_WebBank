package cod.servlet;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import functions.CustomerFunctions;

/**
 * Servlet implementation class OnlineRegistration
 */
@WebServlet("/OnlineRegistration")
public class OnlineRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OnlineRegistration() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String persId = request.getParameter("pid");
		String bDate = request.getParameter("bdate");
		String	eMail = request.getParameter("mail");
		String	psw = request.getParameter("psw");
		String	address = request.getParameter("address");
		String	Phone = request.getParameter("phone");
		
		CustomerFunctions cf = new CustomerFunctions();
		try {
			cf.register(persId, fname, lname, eMail, bDate, address, Phone, psw);
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

}
