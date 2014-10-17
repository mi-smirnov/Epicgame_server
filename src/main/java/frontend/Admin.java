package frontend;

import main.AccountService;

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

    public Admin(AccountService accountService){
        this.accountService = accountService;
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long free = Runtime.getRuntime().freeMemory();
        long max = Runtime.getRuntime().maxMemory();
        long usage = max - free;
        int users = accountService.total_user();
        FileWriter wrt = null;
        try {
            File flt = new File("statistic.txt");
            wrt = new FileWriter(flt);
            wrt.write(String.valueOf(users));
        } catch (IOException e) {
        }finally {
            if (wrt != null) {
                wrt.close();
            }
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response){
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

}
