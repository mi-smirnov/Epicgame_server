import com.sun.istack.internal.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 * Created by smike on 25.10.14.
 */
public class SeleniumTest {

    public void testLogin(@NotNull String url, @NotNull String email, @NotNull String password) {
        WebDriver driver = new HtmlUnitDriver(true);
        driver.get(url);
        WebElement elementEmail = driver.findElement(By.name("myEmail"));
        elementEmail.sendKeys(email);
        WebElement elementPassword = driver.findElement(By.name("password"));
        elementPassword.sendKeys(password);
        elementEmail.submit();
        elementPassword.submit();
    }
}
