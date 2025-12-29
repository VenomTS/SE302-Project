package me.venomts.pages;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

class CardDatabasePageTest
{
    private Playwright _playwright;
    private Browser _browser;
    private Page _page;
    private static final String CardDatabaseURL = "https://ygoprodeck.com/card-database/?num=24&offset=0";
    private CardDatabasePage _cardDatabasePage;

    @BeforeEach
    public void CardDatabaseSetUp()
    {
        _playwright = Playwright.create();
        _browser = _playwright.chromium().launch(new BrowserType.LaunchOptions().setSlowMo(500).setHeadless(false));
        _page = _browser.newPage();

        _page.navigate(CardDatabaseURL);
        _page.waitForURL(CardDatabaseURL);
        _cardDatabasePage = new CardDatabasePage(_page);
    }

    @Test
    void TestSorting()
    {
        _cardDatabasePage.ResetFilters();
        _cardDatabasePage.SelectLanguage("Deutsch");
        _cardDatabasePage.SelectSortDirection("DESC");
        _cardDatabasePage.AssertSortsApplied();
    }

    @Test
    void TestMenus()
    {
        _cardDatabasePage.SelectAttribute("DIVINE");
        _cardDatabasePage.SelectRace("Aqua");
        _cardDatabasePage.AssertFiltersApplied();
    }

    @Test
    void TestInputFields()
    {
        _cardDatabasePage.SelectAttackRange("1000");
        _cardDatabasePage.SelectLevel("5");
        _cardDatabasePage.AssertFieldsApplied();
    }

}