package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class RegisterPage {

    private final SelenideElement usernameInput = $("input[name='username']");
    private final SelenideElement passwordInput = $("input[name='password']");
    private final SelenideElement passwordSubmitInput = $("input[name='passwordSubmit']");
    private final SelenideElement submitBtn = $("button[type='submit']");
    private final SelenideElement successfulRegistrationMessage = $("div[class='form'] p");
    private final SelenideElement signInBtn = $("a[class='form_sign-in']");
    private final SelenideElement errorMessage = $("span[class='form__error']");

    public RegisterPage setUsername(String username) {
        usernameInput.setValue(username);
        return this;
    }

    public RegisterPage setPassword(String password) {
        passwordInput.setValue(password);
        return this;
    }

    public RegisterPage setPasswordSubmit(String password) {
        passwordSubmitInput.setValue(password);
        return this;
    }

    public RegisterPage submitRegistration() {
        submitBtn.click();
        return this;
    }

    public RegisterPage ensureSuccessfulRegistrationNotificationIsPresent(){
        successfulRegistrationMessage.shouldHave(text("Congratulations! You've registered!"));
        return this;
    }

    public void ensureErrorPresent(String errorText){
        errorMessage.shouldHave(text(errorText));
    }

    public LoginPage clickSignInBtn(){
        signInBtn.click();
        return new LoginPage();
    }

}
