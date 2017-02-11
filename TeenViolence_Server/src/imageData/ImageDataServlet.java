package imageData;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import authentication.BuildStaticParameters;

/**
 * Servlet implementation class ImageDataServlet
 */
@WebServlet("/ImageDataServlet")
public class ImageDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageDataServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 try {
			 
			String correctness;
			String param[] = request.getParameterValues("param");
			 
			String isAttempted = param[0]; 
			String responseTime = param[1];
			
			String isPositive = param[2];
			String bgColor = param[3];
			String responseAccurate = param[4];
			
			if(isAttempted.equalsIgnoreCase("false")){
				correctness = "not awswered";
			}else{
				correctness=responseAccurate.equals("true")?"right":"wrong";
			}
			 	 
			String userid = param[5];
			 
			if (BuildStaticParameters.conn == null) {
					BuildStaticParameters.buildConnectionWithSQL();
			}
			 
			String sql;
			sql = "insert into response (respCorrectness,respTimeTaken,respExpectedResult,respUser,respBgColor) values (?,?,?,?,?)";
			 
			PreparedStatement respStmt = BuildStaticParameters.conn.prepareStatement(sql);
			
			respStmt.setString(1, correctness);
			respStmt.setString(2, responseTime);
			respStmt.setString(3, isPositive);
			respStmt.setString(4, userid);
			respStmt.setString(5, bgColor);
			
			respStmt.executeUpdate();
			 
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			 
			 
			 
			response.getWriter().write("{\"result\":true}");
			
		 } catch(SQLException se){
	         //Handle errors for JDBC
	         se.printStackTrace();
	         response.getWriter().write("{\"result\":false, \"error\":" + se.getMessage() + "}");
	      }catch(Exception e){
	         //Handle errors for Class.forName
	         e.printStackTrace();
	         response.getWriter().write("{\"result\":false, \"error\":" + e.getMessage() + "}");
	      }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
