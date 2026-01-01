package me.venomts.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginPage
{
    private final Page _page;

    private final Locator _displayName;
    private final Locator _password;
    private final Locator _loginButton;
    private final Locator _registerButton;

    private final Locator _warningMessage;
    private final Locator _alert;

    private static final String HomePageURL = "https://ygoprodeck.com/";
    private static final String RegisterURL = "https://ygoprodeck.com/register/";
    private static final int RedirectDelay = 3 * 1000; // 3 seconds

    public LoginPage(Page page)
    {
        _page = page;

        _displayName = page.locator("#displayName");
        _password = page.locator("#firstPassWord");
        _loginButton = page.locator(".btn.btn-primary.btn-block").getByText("Login", new Locator.GetByTextOptions().setExact(true));
        _registerButton = page.getByRole(AriaRole.MAIN).getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Sign Up"));
        _warningMessage = page.locator(".warning-msg");
        _alert = page.getByRole(AriaRole.ALERT);
    }

    public void Login(String displayName, String password)
    {
        _displayName.fill(displayName);
        _password.fill(password);
        _loginButton.click();
    }

    public void GoToRegister()
    {
        _registerButton.click();
    }

    public void AssertPasswordTooShort()
    {
        assertThat(_warningMessage).isVisible();
        assertThat(_warningMessage).containsText("Password needs to be a minimum of 5 characters");
    }

    public void AssertLoginSuccessful()
    {
        _page.waitForTimeout(RedirectDelay);
        assertThat(_page).hasURL(HomePageURL);
    }

    public void AssertInvalidCredentials()
    {
        assertThat(_alert).isVisible();
        assertThat(_alert).containsText("Incorrect username/password!");
    }

    public void AssertRegisterRedirect()
    {
        assertThat(_page).hasURL(RegisterURL);
    }
}
