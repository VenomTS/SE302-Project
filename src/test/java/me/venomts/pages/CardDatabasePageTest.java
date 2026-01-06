package me.venomts.pages;

import com.microsoft.playwright.*;
import me.venomts.BrowserSettings;
import me.venomts.enums.*;
import org.junit.jupiter.api.*;

import static org.junit.Assert.assertTrue;

class CardDatabasePageTest
{
    private static final String PageURL = "https://ygoprodeck.com/card-database/";

    private static Playwright _playwright;
    private static Browser _browser;
    private BrowserContext _context;
    private Page _page;
    private CardDatabasePage _cardDatabasePage;

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
        _cardDatabasePage = new CardDatabasePage(_page);
    }

    @AfterEach
    void CloseContext()
    {
        _context.close();
    }

    @Test
    @DisplayName("Search for Cards - Smoke Test")
    void CardFilteringTest()
    {
        _cardDatabasePage.ApplyFilter("Malebranche", Attribute.Dark, Type.Fiend, 1000, 0, 3, 0, 0, Language.English, SortBy.DEF, false);
        assertTrue(_cardDatabasePage.IsFilterApplied());
        _page.waitForTimeout(BrowserSettings.AfterTestDelay);
    }

    @Test
    @DisplayName("Card Display Limit")
    void SetLimitTest()
    {
        _cardDatabasePage.SetLimit(FilterLimit.Limit__50);
        _cardDatabasePage.AssertLimitSet();
        _page.waitForTimeout(BrowserSettings.AfterTestDelay);
    }

    @Test
    @DisplayName("Reset Card Filters")
    void ResetFiltersTest()
    {
        SetLimitTest();
        CardFilteringTest();
        _cardDatabasePage.ResetFilters();
        assertTrue(_cardDatabasePage.IsFilterReset());
        _page.waitForTimeout(BrowserSettings.AfterTestDelay);
    }
}