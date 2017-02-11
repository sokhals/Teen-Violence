package questionnaire;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import authentication.BuildStaticParameters;

/**
 * Servlet implementation class Questionnaire
 */
@WebServlet("/Questionnaire")
public class Questionnaire extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Questionnaire() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reqType = request.getParameter("requestType");
		String sessionQuestion = request.getParameter("questionSession");
		String result = "";
				
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		try {
			if (BuildStaticParameters.conn == null) {
				BuildStaticParameters.buildConnectionWithSQL();
			}
			
			if (reqType.equalsIgnoreCase("request")) {
				String sqlRequest = "";
				if (sessionQuestion.equals("0")){ 
					sqlRequest = "select questionText from questionnaire where questionnaireType = 'B'";
				} else if (sessionQuestion.equals("1")){
					sqlRequest = "select questionText from questionnaire where questionnaireType = 'E'";
				}
				ResultSet rs = BuildStaticParameters.stmt.executeQuery(sqlRequest);
				ArrayList<String> questions = new ArrayList<String>();
				while(rs.next()) {
					questions.add(rs.getString(1));
				}
				result = getQuestionJSON(questions);
			} else if (reqType.equalsIgnoreCase("feedback")) {
				String[] questions = request.getParameterValues("question");
				String[] answers = request.getParameterValues("answer");
				String userID = request.getParameter("userID");
				// fix session ID 
				String sessionID = "";
				String sessionSql = "select usSessionNumber from userSession where usUserId = " + userID + " and usSessionDate in (select max(usSessionDate) from userSession where usUserId = " + userID + ")";
				ResultSet rs = BuildStaticParameters.stmt.executeQuery(sessionSql);
				if(rs.next()){
					sessionID = rs.getString(1);
				} else {
					sessionID = "1";
				}
				String sessionDate = request.getParameter("sessionDate");
				
				String questionType = sessionQuestion.equals("0")? "Start" : "End";
				
				String sql = "insert into questAns (qaSessionId, qaSessionDate, qaUserId, qaQuestion, qaAnswer, questionType) values (?,?,?,?,?,?)";
				PreparedStatement updateAnswers = BuildStaticParameters.conn.prepareStatement(sql);
				for (int i = 0; i < questions.length; i++) {
					updateAnswers.setString(1, sessionID);
					updateAnswers.setString(2, sessionDate);
					updateAnswers.setString(3, userID);
					updateAnswers.setString(4, questions[i]);
					updateAnswers.setString(5, answers[i]);
					updateAnswers.setString(6, questionType);
					updateAnswers.executeUpdate();
				}
				result = "{\"save\":successful}";
			}
			response.getWriter().write(result);
		} catch (SQLException se) {
			se.getStackTrace();
			response.getWriter().write("{\"save1\":\"unsuccessful\", \"error\":" + se.getMessage() + " }");
		} catch (Exception e) {
			e.getStackTrace();
			response.getWriter().write("{\"save2\":\"unsuccessful\", \"error\":" + e.getMessage() + " }");
		}
	}

	private String getQuestionJSON(ArrayList<String> questions) {
		String jsonString = "{questions: [";
		int i = 0;
		for (String s:questions) {
			if (i+1 >= questions.size())
				jsonString = jsonString + "{\"question\":\"" + s + "\"}";
			else
				jsonString =  jsonString + "{\"question\":\"" + s + "\"},";
			i++;
		}
		jsonString = jsonString + "]}";
		return jsonString;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
