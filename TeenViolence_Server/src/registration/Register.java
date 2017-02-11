package registration;

import authentication.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Register
 * for getting the data from the android application
 * and store it into the database
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String type=request.getParameter("queryType");
		String[] registerData = request.getParameterValues("param");
		String result="";
		
		try {
			response.setCharacterEncoding("UTF-8");
			if (BuildStaticParameters.conn == null) {
				BuildStaticParameters.buildConnectionWithSQL();
			}
			
			if(type.equalsIgnoreCase("getAgreement")){
				response.setContentType("plain/text");
				ServletContext ctx=getServletContext();
				File file=new File(ctx.getRealPath("."));
				
				File f = new File(file.getParent()+"/appData/agreement.txt");
				@SuppressWarnings("resource")
				FileInputStream fis = new FileInputStream(f);
				byte[] data = new byte[(int) file.length()];
				fis.read(data);
				
				String agreement=new String(data,"UTF-8");
				response.getWriter().write(agreement);
				return;
			}
			
			response.setContentType("application/json");
			String sql = "select ulUserName from userLogin where "
					+ "ulUserName ='" + registerData[0] + "'";
			ResultSet rs = BuildStaticParameters.stmt.executeQuery(sql);
			
			if(!rs.next()){
				PreparedStatement updateUserLogin = BuildStaticParameters.conn.prepareStatement
					      ("insert into userLogin (ulUserName,ulPassword) values(?,?)");
				updateUserLogin.setString(1,registerData[0]);
				updateUserLogin.setString(2,registerData[1]);
				updateUserLogin.executeUpdate();
				
				String generatedID = "";
				String sqlUserId = "select userLoginId from userLogin where ulUserName = '" + registerData[0] + "'";
				ResultSet rs1 = BuildStaticParameters.stmt.executeQuery(sqlUserId);
				while(rs1.next()) {
					generatedID = rs1.getString("userLoginId");
				}
				
				PreparedStatement updateUser = BuildStaticParameters.conn.prepareStatement(
						"insert into user(userAge, userGender,userEthnicity,userDisability,userEducation,userMobileHandlingExperience,userPsycothereputicMedications,userColorblind,userDetails) values(?,?,?,?,?,?,?,?,?)");
				updateUser.setString(1, registerData[2]);
				updateUser.setString(2, registerData[3]);
				updateUser.setString(3, registerData[4]);
				updateUser.setString(4, registerData[5]);
				updateUser.setString(5, registerData[9]);
				updateUser.setString(6, registerData[6]);
				updateUser.setString(7, registerData[7]);
				updateUser.setString(8, registerData[8]);
				updateUser.setString(9, generatedID);
				updateUser.executeUpdate();
				
				result =  "{\"status\":1,\"userId\":" + generatedID + "}";
				response.getWriter().write(result);
				return;
			}
				result = "{\"status\":0,";
				response.getWriter().write(result + "\"message\":\"user already exist\"}");
				return;
				
		} catch(Exception e) {
			String sql = "delete from userLogin where ulUserName = '" + registerData[0] + "'";
			result = "{\"status\":0,";
			try {
				BuildStaticParameters.stmt.executeUpdate(sql);
			} catch (SQLException e1) {
				response.getWriter().write(result + "\"message\":\""+e1.getMessage()+"\"}");
			} catch (Exception e2) {
				response.getWriter().write(result + "\"message\":\""+e2.getMessage()+"\"}");
			}
			response.getWriter().write(result + "\"message\":\""+e.getMessage()+"\"}");
			return;
		}
		
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
