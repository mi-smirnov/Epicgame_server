package frontend;

import base.AccountService;
import utils.Statistic;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by smike on 10.10.14.
 */
public class AdminServlet extends HttpServlet {

    private AccountService accountService;
    private Statistic statistic;

    public AdminServlet(AccountService accountService, Statistic statistic){
        this.accountService = accountService;
        this.statistic = statistic;
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        statistic.memory();
        statistic.loginedUser();
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response){
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

}
