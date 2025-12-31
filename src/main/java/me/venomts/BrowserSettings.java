package me.venomts;

import com.microsoft.playwright.BrowserType;

public class BrowserSettings
{
    public static final boolean IsHeadlessBrowser = false;
    public static final int BrowserDelay = 500;
    public static final int BrowserTimeout = 100000;
    public static final BrowserType.LaunchOptions LaunchOptions = new BrowserType.LaunchOptions().setHeadless(IsHeadlessBrowser).setSlowMo(BrowserDelay);
}
