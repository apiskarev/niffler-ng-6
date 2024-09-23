package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class MainPage {
  private final ElementsCollection tableRows = $("#spendings tbody").$$("tr");
  private final SelenideElement stats = $("div[id='stat']");
  private final SelenideElement spendings = $("div[id='spendings']");
  private final SelenideElement menuBtn = $("button[aria-label='Menu']");
  private final SelenideElement profileMenuSection = $("a[href*='profile']");

  public EditSpendingPage editSpending(String spendingDescription) {
    tableRows.find(text(spendingDescription)).$$("td").get(5).click();
    return new EditSpendingPage();
  }

  public void checkThatTableContainsSpending(String spendingDescription) {
    tableRows.find(text(spendingDescription)).shouldBe(visible);
  }

  public void ensureDisplayed(){
    stats.shouldBe(visible);
    spendings.shouldBe(visible);
  }

  public MainPage clickMenuBtn(){
    menuBtn.click();
    return this;
  }

  public void clickProfileMenuSection() {
    profileMenuSection.click();
  }

}
