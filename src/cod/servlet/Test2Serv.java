package cod.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import entity.Employee;

/**
 * Servlet implementation class Test2Serv
 */
@WebServlet("/Test2Serv")
public class Test2Serv extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Test2Serv() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
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
		
		
		  GsonBuilder builder = new GsonBuilder();
		  Gson gson =
		  builder.create();
		  String json=gson.toJson(el);
		  System.out.println(json);
		  out.println(json);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		List<String> list = new ArrayList<String>();
	    list.add("item1");
	    list.add("item2");
	    list.add("item3");
	    String json = new Gson().toJson(list);

	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
		
	}

}
