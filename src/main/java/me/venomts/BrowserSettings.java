package me.venomts;

import com.microsoft.playwright.BrowserType;

public class BrowserSettings
{
    private static final boolean IsHeadlessBrowser = false;
    private static final int BrowserDelay = 1000;

    public static final int BrowserTimeout = 100000;
    public static final BrowserType.LaunchOptions LaunchOptions = new BrowserType.LaunchOptions().setHeadless(IsHeadlessBrowser).setSlowMo(BrowserDelay);
}
