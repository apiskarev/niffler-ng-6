package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
  private final SelenideElement usernameInput = $("input[name='username']");
  private final SelenideElement passwordInput = $("input[name='password']");
  private final SelenideElement submitBtn = $("button[type='submit']");
  private final SelenideElement registerBtn = $("a[class='form__register']");
  private final SelenideElement errorMessage = $("p[class='form__error']");

  public MainPage login(String username, String password) {
    usernameInput.setValue(username);
    passwordInput.setValue(password);
    submitBtn.click();
    return new MainPage();
  }

  public RegisterPage clickRegisterBtn() {
    registerBtn.click();
    return new RegisterPage();
  }

  public void ensureErrorPresent(String errorText){
    errorMessage.shouldHave(text(errorText));
  }


}
