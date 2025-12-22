package me.venomts.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.nio.file.Paths;

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

    public boolean IsAccountCreated()
    {
        _registerAlert.waitFor();
        return _registerAlert.textContent().contains("You must activate your account before logging in.");
    }

    public boolean IsAccountExistingAlready()
    {
        _registerAlert.waitFor();
        return _registerAlert.textContent().contains("An account with this username and/or email address already exists");
    }


}
