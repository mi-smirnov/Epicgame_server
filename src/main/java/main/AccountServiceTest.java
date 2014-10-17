package main;

import junit.framework.Assert;
import junit.framework.TestCase;

public class AccountServiceTest extends TestCase {

    public void testAuth() throws Exception {
        AccountService accountService = new AccountService();
        accountService.add("admin@mail.ru","admin");
        String session = accountService.auth("admin@mail.ru","admin");
        System.out.append(session);
        Assert.assertNotNull(session);

    }

    public void testAdd() throws  Exception {
        AccountService accountService = new AccountService();
        boolean acc = accountService.add("admin@mail.ru","admin");
        Assert.assertTrue(acc);
    }
}