package me.venomts.pages;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class DeckBuilderPageTest
{
    private Playwright _playwright;
    private Browser _browser;
    private Page _page;
    private DeckBuilderPage _deckBuilderPage;

    private static final String PageURL = "https://ygoprodeck.com/deckbuilder/";

    @BeforeEach
    void setUp()
    {
        _playwright = Playwright.create();
        _browser = _playwright.firefox().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setSlowMo(500)
        );
        _page = _browser.newPage();

        _page.navigate(PageURL);
        _page.waitForURL(PageURL);

        _deckBuilderPage = new DeckBuilderPage(_page);
    }
    /*
    @Test
    void testImportButton() {
        _deckBuilderPage.assertImportButtonIsVisible();
        _deckBuilderPage.assertImportButtonIsEnabled();
    }

    @Test
    void testDropdownOptions() {
        _deckBuilderPage.assertDropdownOpensWithOptions();
    }

    @Test
    void testImportFromFile() {
        _deckBuilderPage.assertImportFromFileOptionClickable();
    }

    @Test
    void testImportFromURL() {
        _deckBuilderPage.assertImportFromURLOptionClickable();
    }
    */







    @AfterEach
    void tearDown() {
        try {
            if (_page != null) {
                _page.close();
            }
            if (_browser != null) {
                _browser.close();
            }
            if (_playwright != null) {
                _playwright.close();
            }
        } catch (Exception e) {
            System.out.println("Error during cleanup: " + e.getMessage());
        }
    }

}