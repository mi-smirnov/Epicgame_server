package frontend;

import main.AccountService;
import main.Statistic;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by smike on 10.10.14.
 */
public class Admin extends HttpServlet {

    private AccountService accountService;
    private Statistic statistic;

    public Admin(AccountService accountService, Statistic statistic){
        this.accountService = accountService;
        this.statistic = statistic;
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        statistic.memory();
        statistic.logined_users();
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response){
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

}
