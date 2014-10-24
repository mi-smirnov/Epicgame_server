import base.AccountService;
import frontend.AccountServiceImpl;
import junit.framework.Assert;
import junit.framework.TestCase;

public class AccountServiceImplTest extends TestCase {

    public void testAddCorrect() throws  Exception {
        AccountService accountService = new AccountServiceImpl();
        boolean acc = accountService.add("admin@mail.ru","admin");
        Assert.assertTrue(acc);
    }

    public void testAddIncorrectDuplicateEmail() throws  Exception {
        AccountService accountService = new AccountServiceImpl();
        accountService.add("admin@mail.ru","admin");
        boolean acc = accountService.add("admin@mail.ru","admin");
        Assert.assertTrue(acc);
    }

    public void testAuthCorrect() throws Exception {
        AccountService accountService = new AccountServiceImpl();
        accountService.add("admin@mail.ru","admin");
        String session = accountService.auth("admin@mail.ru","admin");
        System.out.append(session);
        Assert.assertNotNull(session);
    }

    public void testAuthIncorrectPassword() throws Exception {
        AccountService accountService = new AccountServiceImpl();
        accountService.add("admin@mail.ru","admin");
        String session = accountService.auth("admin@mail.ru", "");
        System.out.append(session);
        Assert.assertNotNull(session);

    }

    public void testAuthIncorrectEmail() throws Exception {
        AccountService accountService = new AccountServiceImpl();
        accountService.add("admin@mail.ru","admin");
        String session = accountService.auth("aa@mail.ru", "admin");
        System.out.append(session);
        Assert.assertNotNull(session);

    }


    public int testTotalUser() throws Exception {
        AccountService accountService = new AccountServiceImpl();
        accountService.add("admin@mail.ru","admin");
        accountService.auth("admin@mail.ru","admin");
        int users = accountService.totalUser();
        Assert.assertEquals(users, 1);
        return 0;
    }
}