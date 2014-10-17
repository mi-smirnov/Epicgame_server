package test;

import frontend.AccountServiceImpl;
import junit.framework.Assert;
import junit.framework.TestCase;

public class AccountServiceImplTest extends TestCase {

    public void testAuth() throws Exception {
        AccountServiceImpl accountServiceImpl = new AccountServiceImpl();
        accountServiceImpl.add("admin@mail.ru","admin");
        String session = accountServiceImpl.auth("admin@mail.ru","admin");
        System.out.append(session);
        Assert.assertNotNull(session);

    }

    public void testAdd() throws  Exception {
        AccountServiceImpl accountServiceImpl = new AccountServiceImpl();
        boolean acc = accountServiceImpl.add("admin@mail.ru","admin");
        Assert.assertTrue(acc);
    }
}