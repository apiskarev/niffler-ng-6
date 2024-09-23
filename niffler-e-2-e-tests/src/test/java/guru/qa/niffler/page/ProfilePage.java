package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static javax.management.Query.attr;

public class ProfilePage {

    private final SelenideElement usernameInput = $("input[name='username']");
    private final SelenideElement nameInput = $("input[name='name']");
    private final SelenideElement saveChangesBtn = $("button[type='submit']");
    private final SelenideElement showArchivedSwitchBtn = $("input[type='checkbox']");
    private final SelenideElement categoryInput = $("input[name='category']");
    private final ElementsCollection categoriesList = $$("div[class*='MuiChip-root']");
    private final SelenideElement archiveConfirmBtn = $x("//button[text()='Archive']");
    private final SelenideElement successNotificationBar = $("[class*='MuiAlert-message'] > div");

    public ProfilePage setUsername(String username){
        usernameInput.setValue(username);
        return this;
    }

    public ProfilePage setName(String name){
        nameInput.setValue(name);
        return this;
    }

    public ProfilePage saveChanges(){
        saveChangesBtn.click();
        return this;
    }

    public ProfilePage switchShowArchived(){
        showArchivedSwitchBtn.click();
        return this;
    }

    public ProfilePage addNewCategory(String category){
        categoryInput.setValue(category);
        categoryInput.sendKeys(Keys.ENTER);
        return this;
    }

    public ProfilePage markCategoryArchived(String categoryName){
        val archiveBtn = categoriesList.filter(text(categoryName))
                .first()
                .parent()
                .$("[aria-label='Archive category']");

        archiveBtn.click();
        archiveConfirmBtn.click();
        return this;
    }

    public ProfilePage ensureSuccessArchiveNotificationIsDisplayed(String categoryTitle){
        successNotificationBar.shouldHave(text("Category " + categoryTitle + " is archived"));
        return this;
    }

    public ProfilePage clickShowArchivedSwitchBtn(){
        showArchivedSwitchBtn.click();
        return this;
    }

    public void ensureArchivedCategoryFoundWithinCategoriesList(String categoryTitle) {
        categoriesList.filter(text(categoryTitle))
                .stream().findFirst()
                .orElseThrow(() -> new Error("Category " + categoryTitle + " was not found within list"));
    }

    public void ensureActiveCategoryFoundWithinCategoryList(String categoryTitle) {
        categoriesList.filter(text(categoryTitle))
                .stream()
                .findFirst()
                .orElseThrow(() -> new Error("Category " + categoryTitle + " was not found within list"));
    }
}
