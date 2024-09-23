package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.page.MainPage;
import lombok.val;
import org.junit.jupiter.api.Test;

public class LoginWebTest {

    private static final Config CFG = Config.getInstance();

    @Test
    void mainPageShouldBeDisplayedAfterSuccessfulLogin(){
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .login("duck", "1234");

        val mainPage = new MainPage();
        mainPage.ensureDisplayed();
    }

    @Test
    void userShouldStayOnLoginPageAfterLoginWithBadCredentials(){
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .login("duck", "12345");

        val loginPage = new LoginPage();
        loginPage.ensureErrorPresent("Неверные учетные данные пользователя");
    }

}
