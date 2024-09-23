package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import com.github.javafaker.Faker;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.extension.BrowserExtension;
import guru.qa.niffler.page.LoginPage;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(BrowserExtension.class)
public class RegisterUserWebTest {

    public static final Config CFG = Config.getInstance();
    private final Faker faker = new Faker();

    @Test
    void shouldRegisterNewUser(){
        val username = faker.name().username();
        val password = faker.internet().password(true);

        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .clickRegisterBtn()
                .setUsername(username)
                .setPassword(password)
                .setPasswordSubmit(password)
                .submitRegistration()
                .ensureSuccessfulRegistrationNotificationIsPresent();
    }

    @Test
    void shouldNotRegisterUserWithExistingUsername(){
        val username = faker.name().username();
        val password = faker.internet().password(true);

        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .clickRegisterBtn()
                .setUsername(username)
                .setPassword(password)
                .setPasswordSubmit(password)
                .submitRegistration()
                .ensureSuccessfulRegistrationNotificationIsPresent()
                .clickSignInBtn()
                .clickRegisterBtn()
                .setUsername(username)
                .setPassword(password)
                .setPasswordSubmit(password)
                .submitRegistration()
                .ensureErrorPresent("Username `" + username + "` already exists");
    }

    @Test
    void shouldShowErrorIfPasswordAndConfirmPasswordAreNotEqual(){
        val username = faker.name().username();
        val password = faker.internet().password(true);

        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .clickRegisterBtn()
                .setUsername(username)
                .setPassword(password)
                .setPasswordSubmit(password + "a")
                .submitRegistration()
                .ensureErrorPresent("Passwords should be equal");
    }

}
