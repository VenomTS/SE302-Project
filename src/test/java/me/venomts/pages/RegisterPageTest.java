package me.venomts.pages;

import com.microsoft.playwright.*;
import me.venomts.BrowserSettings;
import org.junit.jupiter.api.*;

import static org.junit.Assert.assertFalse;

class RegisterPageTest
{
    // NOTE: Tests may FAIL due to Captcha system that the site uses to prevent bot activity
    private static final String PageURL = "https://ygoprodeck.com/register/";

    private static Playwright _playwright;
    private static Browser _browser;
    private BrowserContext _context;
    private Page _page;
    private RegisterPage _registerPage;

    @BeforeAll
    static void LaunchBrowser()
    {
        _playwright = Playwright.create();
        _browser = _playwright.chromium().launch(BrowserSettings.LaunchOptions);
    }

    @AfterAll
    static void CloseBrowser()
    {
        _playwright.close();
    }

    @BeforeEach
    void CreateContextAndPage()
    {
        _context = _browser.newContext();
        _page = _context.newPage();
        _page.navigate(PageURL);
        _registerPage = new RegisterPage(_page);
    }

    @AfterEach
    void CloseContext()
    {
        _context.close();
    }

    @Test
    void RegisterNewAccountTest()
    {
        String displayName = "SoftwareTestingAndMaintenance";
        String mail = "the.waster.mail@gmail.com";
        String password = "SoftwareTesting123!";

        _registerPage.Register(displayName, mail, password);
        _registerPage.AssertAccountCreated();
    }

    @Test
    void AccountAlreadyExistsTest()
    {
        String displayName = "Kaibaman";
        String mail = "seto@kaibacorp.com";
        String password = "GoodPassword123";
        _registerPage.Register(displayName, mail, password);
        _registerPage.AssertAccountAlreadyExists();
    }

    @Test
    void UsernameContainsInvalidCharactersTest()
    {
        String displayName = "Kaibaman!";
        String mail = "seto@kaibacorp.com";
        String password = "GoodPassword123";
        _registerPage.Register(displayName, mail, password);
        _registerPage.AssertUsernameContainsInvalidCharacters();
    }

    @Test
    void ShortPasswordTest()
    {
        String displayName = "Kaibaman";
        String mail = "seto@kaibacorp.com";
        String password = "1234";
        _registerPage.Register(displayName, mail, password);
        String errorMessage = _registerPage.GetPasswordErrorMessage();
        assertFalse(errorMessage.isEmpty());
    }

    @Test
    void PasswordMismatchTest()
    {
        String displayName = "Kaibaman";
        String mail = "seto@kaibacorp.com";
        String password = "GoodPassword";
        String confirmPassword = "BadPassword";
        _registerPage.Register(displayName, mail, password, confirmPassword);
        _registerPage.AssertPasswordMismatch();
    }

    @Test
    void GoToLoginTest()
    {
        _registerPage.GoToLogIn();
        _registerPage.AssertLoginRedirect();
    }

    @Test
    void InvalidMailTest()
    {
        String displayName = "Kaibaman";
        String mail = "seto@.com";
        String password = "12345";
        _registerPage.Register(displayName, mail, password);
        String errorMessage = _registerPage.GetMailErrorMessage();
        assertFalse(errorMessage.isEmpty());
    }
}