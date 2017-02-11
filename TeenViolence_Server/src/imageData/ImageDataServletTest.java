package imageData;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ImageDataServletTest {
	
	ImageDataServlet img = new ImageDataServlet();
	@Mock
	HttpServletRequest request;
	@Mock
	HttpServletResponse response;

	@Before
	 public void setUp() throws Exception {
	  MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testDoGetHttpServletRequestHttpServletResponse() {
		request = mock(HttpServletRequest.class);       
	    response = mock(HttpServletResponse.class);
	    String[] result = {"true", "0.01", "true", "red", "true", "1"};
	    when(request.getParameterValues("param")).thenReturn(result);
	    
	    assertEquals(null, response.getContentType());
	}

}
