package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by smike on 11.10.14.
 */
public class Statistic {
    private AccountService accountService;

    public Statistic(AccountService accountService){ this.accountService = accountService;}

    public void fileWriter(String path, String entry) throws IOException{
        FileWriter wrt = null;
        try {
            File flt = new File(path);
            wrt = new FileWriter(flt);
            wrt.write(entry);
            //wrt.write(String.valueOf(usage));
        } catch (IOException e) {
        }finally {
            if (wrt != null) {
                wrt.close();
            }
        }
    }
    public void memory() throws IOException {
        long free = Runtime.getRuntime().freeMemory();
        long max = Runtime.getRuntime().maxMemory();
        long usage = max - free;
        fileWriter("memory.txt", String.valueOf(usage));
    }

    public void logined_users() throws IOException {
        int users = accountService.total_user();
        fileWriter("users.txt", String.valueOf(users));
    }
}
