package me.venomts.pages;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

class RegisterPageTest
{
    private Playwright _playwright;
    private Browser _browser;
    private Page _page;
    private RegisterPage _registerPage;

    private static final String PageURL = "https://ygoprodeck.com/register/";

    @BeforeEach
    void setUp()
    {
        _playwright = Playwright.create();
        _browser = _playwright.firefox().launch(new BrowserType.LaunchOptions());
        _page = _browser.newPage();

        _page.navigate(PageURL);
        _page.waitForURL(PageURL);

        _registerPage = new RegisterPage(_page);
    }

    @Test
    void RegisterNewAccount()
    {
        /*
        Moramo skontati email koji ce proci verifikaciju na prezentaciji jer ne smijemo koristiti testne mails
         */

        String userName = "Some Testing Username";
        String mail = "Some Testing Mail";
        String password = "Some Testing Password";

        _registerPage.FillRegisterInformation(userName, mail, password);
        _registerPage.ClickRegisterButton();

        boolean isSuccessfulRegistration = _registerPage.IsAccountCreated();
        assertTrue(isSuccessfulRegistration);
    }

    @Test
    void RegisterExistingAccount()
    {
        String userName = "Kaibaman";
        String mail = "meandyou@wearetestingthis.com";
        String password = "VeryStrangeLookingPassword";

        _registerPage.FillRegisterInformation(userName, mail, password);
        _registerPage.ClickRegisterButton();

        boolean isExistingAccount = _registerPage.IsAccountExistingAlready();
        _page.screenshot(new Page.ScreenshotOptions().setFullPage(true).setPath(Paths.get("fail.jpg")));
        assertTrue(isExistingAccount);
    }
}