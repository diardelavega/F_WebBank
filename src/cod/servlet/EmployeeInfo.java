package cod.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.Employee;
import functions.DirectorFunctions;

/**
 * Servlet implementation class EmployeeInfo
 */
@WebServlet("/EmployeeInfo.do")
public class EmployeeInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO get form parameters to narrow the search
		//get array List of Employee obj & store it to session
		HttpSession ses = request.getSession();
		//int primeKey = (int) ses.getAttribute("primeKey");
		DirectorFunctions df = new DirectorFunctions ();
		List<Employee> el = df.getEmployees();
		ses.setAttribute("el", el);
		response.sendRedirect("./jspFiles/Test2.jsp");
		
	}

}
