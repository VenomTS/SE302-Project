package me.venomts.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;


import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CardDatabasePage
{
    private Page page;

    private Locator _filterAttribute;
    private Locator _filterRace;
    private Locator _advancedFiltersButton;


    public CardDatabasePage(Page page)
    {
        this.page = page;
        _filterAttribute = page.locator("#filter-attribute");
        _filterRace = page.locator("#filter-race");
        _advancedFiltersButton = page.locator(".btn.btn-primary.filterlimit");
    }

    //Used only inside this class:
    private void ToggleAdvancedFiltersButton()
    {
        _advancedFiltersButton.click();
    }
    //In actual tests:
    public void SelectAttribute(String attribute)
    {
        ToggleAdvancedFiltersButton();
        _filterAttribute.selectOption(attribute);
    }
    //In actual tests:
    public void SelectRace(String race)
    {
        ToggleAdvancedFiltersButton();
        _filterRace.click();
        _filterRace.getByText(race).click();
    }

    public void AssertFiltersApplied()
    {
        String currentURL = page.url();
        assertThat(page).hasURL("https://ygoprodeck.com/card-database/?attribute=DIVINE&race=Aqua&num=24&offset=0");
    }

}