package cod.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import entity.Employee;

import java.lang.StackOverflowError;

/**
 * Servlet implementation class Test
 */
@WebServlet(name = "Test.do", urlPatterns = { "/Test.do" })
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Test() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// System.out.println("WE are in");

		PrintWriter out = response.getWriter();
		List<Employee> el = new ArrayList<Employee>();
		Employee e = new Employee();
		e.setAddress("matahari");
		e.seteMail("mm@webank.com");
		e.setFname("Marko");
		e.setLname("spinoza");
		el.add(e);

		e = new Employee();
		e.setAddress("shijak");
		e.seteMail("mmubarak@hua.com");
		e.setFname("Espinoza");
		e.setLname("Mikael");
		el.add(e);
		/*
		 * GsonBuilder builder = new GsonBuilder(); Gson gson =
		 * builder.create(); System.out.println(gson.toJson(el));
		 */

		String json = new Gson().toJson(el);
		// System.out.println(json);

		// out.println(json);

		response.setContentType("application/json");
		// response.setCharacterEncoding("UTF-8");
		// response.getWriter().write(json);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		List<Employee> el = new ArrayList<Employee>();
		Employee e = new Employee();
		e.setAddress("Kolonje");
		e.seteMail("mm@webank.com");
		e.setFname("Marko");
		e.setLname("spinoza");
		el.add(e);

		e = new Employee();
		e.setAddress("shijak");
		e.seteMail("mmubarak@hua.com");
		e.setFname("Espinoza");
		e.setLname("Mikael");
		el.add(e);
		String json = new Gson().toJson(el);
		
		String jo = request.getReader().readLine();
		
		 System.out.println("aaaaaa "+jo);

		/*
		 * String foo = request.getParameter("foo"); String bar =
		 * request.getParameter("bar"); String baz =
		 * request.getParameter("baz");
		 * 
		 * System.out.println("-------> " + foo + " " + bar + " " + baz);
		 * 
		 * boolean ajax = "XMLHttpRequest".equals(request
		 * .getHeader("X-Requested-With")); if (ajax) {
		 * System.out.println("ajax"); } else { System.out.println("PSV"); }
		 */

		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);

	}

}
