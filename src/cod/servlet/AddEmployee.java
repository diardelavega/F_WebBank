package cod.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import functions.DirectorFunctions;

/**
 * Servlet implementation class AddEmployee
 */
@WebServlet("/AddEmployee.do")
public class AddEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(Log.class);
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddEmployee() {
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
		HttpSession session = request.getSession(false);
		Integer primeKey = null;
		logger.info("Adding new Employee");
		try {
			primeKey = (Integer) request.getSession().getAttribute(
					"primeKey");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (primeKey == null) {
//			session.setAttribute("validity", "invalid credentials ");
			response.sendRedirect("jspFiles/Test.jsp");
		}
		DirectorFunctions df = new DirectorFunctions(primeKey);
		long empId = df.createEmployee(request.getParameter("fname"),
				request.getParameter("lname"), request.getParameter("address"),
				request.getParameter("pos"), request.getParameter("mail"),
				request.getParameter("password"));

		if (empId == -1) {
			response.sendRedirect("jspFiles/Error.jsp");
		} else {
			session.setAttribute("validity", "employee registered");
		}
		/* fname=request.getParameter("fname"); */
		response.sendRedirect("jspFiles/director.jsp");

	}

}
