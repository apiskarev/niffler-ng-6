package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.annotation.Category;
import guru.qa.niffler.jupiter.extension.BrowserExtension;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.page.MainPage;
import guru.qa.niffler.page.ProfilePage;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(BrowserExtension.class)
public class ProfileWebTest {

    public static final Config CFG = Config.getInstance();

    @Category(
            username = "duck",
            archived = false)
    @Test
    void archivedCategoryShouldPresentInCategoriesList(CategoryJson categoryJson) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .login("duck", "1234");

        val mainPage = new MainPage();
        mainPage.clickMenuBtn()
                .clickProfileMenuSection();

        val profilePage = new ProfilePage();
        profilePage.markCategoryArchived(categoryJson.name())
                .ensureSuccessArchiveNotificationIsDisplayed(categoryJson.name())
                .clickShowArchivedSwitchBtn()
                .ensureArchivedCategoryFoundWithinCategoriesList(categoryJson.name());
    }

    @Category(
            username = "duck",
            archived = false)
    @Test
    void activeCategoryShouldPresentInCategoriesList(CategoryJson categoryJson) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .login("duck", "1234");

        val mainPage = new MainPage();
        mainPage.clickMenuBtn()
                .clickProfileMenuSection();

        val profilePage = new ProfilePage();
        profilePage.ensureActiveCategoryFoundWithinCategoryList(categoryJson.name());
    }

}
