package parameter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mock;

public class FetchInstructionTest {
	
	FetchInstruction fi = new FetchInstruction();
	@Mock
	HttpServletRequest request;
	@Mock
	HttpServletResponse response;

	@Test
	public void testDoGetHttpServletRequestHttpServletResponse() {
		request = mock(HttpServletRequest.class);       
	    response = mock(HttpServletResponse.class);
	    
	    assertEquals(null, response.getContentType());
	}

}
