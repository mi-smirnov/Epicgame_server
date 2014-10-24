import base.AccountService;
import frontend.*;
import junit.framework.Assert;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;

/**
 * Created by smike on 23.10.14.
 */
public class FrontendTest {
    final private static HttpServletRequest request = mock(HttpServletRequest.class);
    final private static HttpServletResponse response = mock(HttpServletResponse.class);
    AccountService accountservice = new AccountServiceImpl();
    SignInServlet sigIn = new SignInServlet(accountservice);
    @Test
    public void testDoPostSignIn() throws Exception {
        final StringWriter stringWrite = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWrite);
        final String url = "/api/v1/auth/signin" ;
        when(response.getWriter()).thenReturn(writer);
        HttpSession session;
        when(session = request.getSession()).thenReturn(session);
        when(request.getPathInfo()).thenReturn(url);
        sigIn.doPost(request, response);
        verify(request, atLeastOnce()).getParameter("email");
        verify(request, atLeastOnce()).getParameter("password");

        Assert.assertTrue(stringWrite.toString().contains(""));
    }

}
