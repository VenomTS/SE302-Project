package me.venomts.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.options.AriaRole;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class RegisterPage
{
    private Locator _registerAlert;
    private Locator _displayName;
    private Locator _mail;
    private final Locator _password;
    private Locator _confirmPassword;
    private Locator _createAccountButton;

    public RegisterPage(Page page)
    {
        _registerAlert = page.getByRole(AriaRole.ALERT);
        _displayName = page.locator("#displayName");
        _mail = page.locator("#regEmail");
        _password = page.locator("#firstPassWord");
        _confirmPassword = page.locator("#secondPassWord");
        _createAccountButton = page.getByText("Create Account");
    }

    public void FillRegisterInformation(String displayName, String mail, String password)
    {
        _displayName.fill(displayName);
        _mail.fill(mail);
        _password.fill(password);
        _confirmPassword.fill(password);
    }

    public void ClickRegisterButton()
    {
        _createAccountButton.click();
    }

    public void AssertAccountCreated()
    {
        assertThat(_registerAlert).containsText("You must activate your account before logging in.");
    }

    public void AssertAccountAlreadyExists()
    {
        assertThat(_registerAlert).containsText("An account with this username and/or email address already exists");
    }
}
