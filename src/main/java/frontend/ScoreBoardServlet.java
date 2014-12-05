package frontend;

import org.json.simple.JSONObject;
import utils.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by smike on 26.11.14.
 */
public class ScoreBoardServlet extends HttpServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException, ServletException{
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        JSONObject obj = new JSONObject();
        obj.put("email", "smirnov@mail.ru");
        obj.put("score", "120");
        response.getWriter().println(obj.toString());
    }
}
