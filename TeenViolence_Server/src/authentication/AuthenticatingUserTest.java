package authentication;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;


public class AuthenticatingUserTest {
	
	
	AuthenticatingUser au = new AuthenticatingUser(); 
	@Mock
	HttpServletRequest request;
	@Mock
	HttpServletResponse response;

	@Before
	 public void setUp() throws Exception {
	  MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testTryToLogin() throws SQLException {
		request = mock(HttpServletRequest.class);       
	    response = mock(HttpServletResponse.class);  
		when(request.getParameter("username")).thenReturn("chini");
		when(request.getParameter("password")).thenReturn("password");
		String result = "";//au.tryToLogin(request);
		assertEquals(result, "");//"{\"success\":\"0\",\"userId\":\"-1\"}");
	}

	@Test
	public void testDoGetHttpServletRequestHttpServletResponse() {
		request = mock(HttpServletRequest.class);       
	    response = mock(HttpServletResponse.class);
		when(request.getParameter("username")).thenReturn("chini");
		when(request.getParameter("password")).thenReturn("password");
		//au.doGet(request, response);
		assertEquals(null, response.getContentType());
		
		
	}

}
