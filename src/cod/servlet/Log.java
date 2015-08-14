package cod.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.GeneralFunctions;
import comon.LogIn;
import db.DBHandler;
import entity.Customers;
import entity.Employee;

/**
 * Servlet implementation class Log
 */
@WebServlet("/Log.do")
public class Log extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(Log.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Log() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// try {
		// // Begin unit of work
		// DBHandler.getSessionFactory().getCurrentSession()
		// .beginTransaction();
		//
		// // Process request and render page...
		//
		// // End unit of work
		// DBHandler.getSessionFactory().getCurrentSession().getTransaction()
		// .commit();
		// } catch (Exception ex) {
		// DBHandler.getSessionFactory().getCurrentSession().getTransaction()
		// .rollback();
		// if (ServletException.class.isInstance(ex)) {
		// throw (ServletException) ex;
		// } else {
		// throw new ServletException(ex);
		// }
		// }

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String usr = request.getParameter("usr");
		String psw = request.getParameter("psw");
		HttpSession session = request.getSession(false);
		session.setAttribute("validity", null);
		String destination = "jspFiles/LogIn.jsp";
//		RequestDispatcher rd;

		if (usr.equals("") || psw.equals("")) {
			session.setAttribute("validity", "invalid credentials ");
		} else {
			String domain = checkMail(usr);
			if (domain == null) {
				session.setAttribute("validity", "not_ok");
			} else {
				LogIn log = new LogIn();
				if (domain.equals("webank")) {
					Employee e = log.logInEmp(usr, psw);
					if (e != null) {
						session = request.getSession(true);
						session.setAttribute("primeKey", e.getEmpId());
						session.setAttribute("name", e.getFname());
						destination = "jspFiles/Test.jsp";
					} else {
						session.setAttribute("validity", "user not found");
					}
				} else {
					Customers c = log.logInCust(usr, psw);
					if (c != null) {
						session = request.getSession(true);
						session.setAttribute("primeKey", c.getPersonalId());
						session.setAttribute("name", c.getFname());
						destination = "jspFiles/Test.jsp";
					} else {
						session.setAttribute("validity", "user not found");
					}
				}
			}
		}

		// session.setAttribute("primeKey", "12345");
		// session.setAttribute("name", "MARIO");
		// rd = request.getRequestDispatcher("jspFiles/Test.jsp");
		// log.closeSession();
		// rd = request.getRequestDispatcher(destination);
		// rd.forward(request, response);
		response.sendRedirect(destination);
	}

	private String checkMail(String mail) {

		String[] temp;
		if ((mail.contains("@")) && (mail.contains("."))) {
			temp = mail.split("[@.]");
			return temp[1];
		}
		return null;
	}

}