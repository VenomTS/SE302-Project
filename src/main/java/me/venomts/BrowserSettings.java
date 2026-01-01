package me.venomts;

import com.microsoft.playwright.BrowserType;

public class BrowserSettings
{
    private static final boolean IsHeadlessBrowser = true;

    // This delay is based on ResetFiltersTest, other tests can work faster
    private static final int BrowserDelay = 500;

    public static final BrowserType.LaunchOptions LaunchOptions = new BrowserType.LaunchOptions().setHeadless(IsHeadlessBrowser).setSlowMo(BrowserDelay);
}
