package authentication;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AuthenticatingUser
 */
@WebServlet("/AuthenticatingUser")
public class AuthenticatingUser extends HttpServlet {
	
	
	
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AuthenticatingUser() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		String results = "";
		String id="-1";
		try {
			if (BuildStaticParameters.conn == null) {
				BuildStaticParameters.buildConnectionWithSQL();
			}
			response.setCharacterEncoding("UTF-8");
			
			String username = request.getParameter("username");

			String password = request.getParameter("password");
			String result = "0";
			String sql;
			sql = "select userLoginId from userLogin where ulUserName = '" + username + "'and ulPassword = '" + password + "'";
			ResultSet rs = BuildStaticParameters.stmt.executeQuery(sql);
			
			while(rs.next()){
				result = "1";
				id=rs.getString(1);
			}
			
			results = ("{\"success\":"+result+",\"userId\":"+id+"}");
			response.getWriter().write(results);
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("{\"success\":\"0\",\"userId\":"+id+"}");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
