package me.venomts.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class RegisterPage
{
    private final Page _page;

    private final Locator _alert;
    private final Locator _displayName;
    private final Locator _mail;
    private final Locator _password;
    private final Locator _confirmPassword;
    private final Locator _registerButton;
    private final Locator _logInButton;

    public RegisterPage(Page page)
    {
        _page = page;
        _alert = page.getByRole(AriaRole.ALERT);
        _displayName = page.locator("#displayName");
        _mail = page.locator("#regEmail");
        _password = page.locator("#firstPassWord");
        _confirmPassword = page.locator("#secondPassWord");
        _registerButton = page.getByText("Create Account");
        _logInButton = page.getByText("Log In", new Page.GetByTextOptions().setExact(true));
    }

    public void Register(String displayName, String mail, String password)
    {
        _displayName.fill(displayName);
        _mail.fill(mail);
        _password.fill(password);
        _confirmPassword.fill(password);
        _registerButton.click();
    }

    public void Register(String displayName, String mail, String password, String confirmPassword)
    {
        _displayName.fill(displayName);
        _mail.fill(mail);
        _password.fill(password);
        _confirmPassword.fill(confirmPassword);
        _registerButton.click();
    }

    public void GoToLogIn()
    {
        _logInButton.click();
    }

    public void AssertAccountCreated()
    {
        assertThat(_alert).isVisible();
        assertThat(_alert).containsText("You must activate your account before logging in.");
    }

    public void AssertAccountAlreadyExists()
    {
        assertThat(_alert).isVisible();
        assertThat(_alert).containsText("An account with this username and/or email address already exists");
    }

    public void AssertUsernameContainsInvalidCharacters()
    {
        assertThat(_alert).isVisible();
        assertThat(_alert).containsText("Username can only contain letters, numbers, and spaces.");
    }

    public String GetPasswordErrorMessage()
    {
        return _password.evaluate("el => el.validationMessage").toString();
    }

    public void AssertPasswordMismatch()
    {
        assertThat(_alert).isVisible();
        assertThat(_alert).containsText("Passwords do not match");
    }

    public void AssertLoginRedirect()
    {
        assertThat(_page).hasURL("https://ygoprodeck.com/login/");
    }

    public String GetMailErrorMessage()
    {
        return _mail.evaluate("el => el.validationMessage").toString();
    }
}
