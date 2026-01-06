package me.venomts.pages;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import me.venomts.BrowserSettings;
import org.junit.jupiter.api.*;

public class LoginPageTest
{
    // NOTE: Tests may FAIL due to Captcha system that the site uses to prevent bot activity
    private static final String PageURL = "https://ygoprodeck.com/login/";

    private static Playwright _playwright;
    private static Browser _browser;
    private BrowserContext _context;
    private Page _page;
    private LoginPage _loginPage;

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
        _loginPage = new LoginPage(_page);
    }

    @AfterEach
    void CloseContext()
    {
        _context.close();
    }

    @Test
    @DisplayName("Log In with Existing Account - Smoke Test")
    public void LoginExistingAccountTest()
    {
        String displayName = "SoftwareTestingAndMaintenance";
        String password = "SoftwareTesting123!";

        _loginPage.Login(displayName, password);
        _loginPage.AssertLoginSuccessful();
        _page.waitForTimeout(BrowserSettings.AfterTestDelay);
    }

    @Test
    @DisplayName("Login with too short Password - Negative Test")
    public void PasswordTooShortTest()
    {
        String displayName = "SoftwareTestingAndMaintenance";
        String password = "1234";
        _loginPage.Login(displayName, password);
        _loginPage.AssertPasswordTooShort();
        _page.waitForTimeout(BrowserSettings.AfterTestDelay);
    }

    @Test
    @DisplayName("Login with Unregistered Email")
    public void InvalidMailTest()
    {
        String mail = "ovojejakodugacakmailkojivjerovatnonikonema@honeymoon.com";
        String password = "OvoJeTacanPassword123";
        _loginPage.Login(mail, password);
        _loginPage.AssertInvalidCredentials();
        _page.waitForTimeout(BrowserSettings.AfterTestDelay);
    }

    @Test
    @DisplayName("Login with Invalid Password")
    public void InvalidPasswordTest()
    {
        String mail = "SoftwareTestingAndMaintenance";
        String password = "SoftwareTesting123";
        _loginPage.Login(mail, password);
        _loginPage.AssertInvalidCredentials();
        _page.waitForTimeout(BrowserSettings.AfterTestDelay);
    }

    @Test
    @DisplayName("Redirect from Register Page to Login Page")
    public void SignupRedirectTest()
    {
        _loginPage.GoToRegister();
        _loginPage.AssertRegisterRedirect();
        _page.waitForTimeout(BrowserSettings.AfterTestDelay);
    }
}
