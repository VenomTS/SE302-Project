package me.venomts.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;


import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CardDatabasePage
{
    private Page page;

    private Locator _filterAttribute;
    private Locator _filterRace;
    private Locator _AdvancedFiltersButton;
    private Locator _ATKNumber;
    private Locator _cardLevelNumber;
    private Locator _resetFilters;
    private Locator _filterLanguage;
    private Locator _filterSortDirection;


    public CardDatabasePage(Page page)
    {
        this.page = page;
        _filterAttribute = page.locator("#filter-attribute");
        _filterRace = page.locator("#filter-race");
        _AdvancedFiltersButton = page.locator(".btn.btn-primary.filterlimit ");
        _ATKNumber = page.locator("#atkLabel");
        _cardLevelNumber = page.locator("#cardlevel");
        _resetFilters = page.locator("#resetFilters");
        _filterLanguage = page.locator("#filter-language");
        _filterSortDirection = page.locator("#filter-sort-direction");
    }

    //Used only inside this class:
    public void OpenAdvancedFiltersButton()
    {
        String filterButtonExpanded = _AdvancedFiltersButton.getAttribute("aria-expanded");
        if ("false".equals(filterButtonExpanded))
            _AdvancedFiltersButton.click();
    }

    public void ResetFilters()
    {
        OpenAdvancedFiltersButton();
        _resetFilters.click();
    }

    //In actual tests:
    public void SelectAttribute(String attribute)
    {
        OpenAdvancedFiltersButton();
        _filterAttribute.selectOption(attribute);
    }
    //In actual tests:
    public void SelectRace(String race)
    {
        OpenAdvancedFiltersButton();
        _filterRace.selectOption(race);
    }

    public void SelectAttackRange(String attack)
    {
        OpenAdvancedFiltersButton();
        _ATKNumber.fill(attack);
    }

    public void SelectLevel(String level)
    {
        OpenAdvancedFiltersButton();
        _cardLevelNumber.fill(level);
    }

    public void SelectLanguage(String language)
    {
        OpenAdvancedFiltersButton();
        _filterLanguage.selectOption(language);
    }

    public void SelectSortDirection(String direction)
    {
        OpenAdvancedFiltersButton();
        _filterSortDirection.selectOption(direction);
    }

    //Assertions:

    public void AssertSortsApplied()
    {
        assertThat(page).hasURL("https://ygoprodeck.com/card-database/?sortorder=desc&language=de&num=24&offset=0");
    }

    public void AssertFieldsApplied()
    {
        assertThat(page).hasURL("https://ygoprodeck.com/card-database/?atk=1000&level=5&num=24&offset=0");
    }

    public void AssertFiltersApplied()
    {
        assertThat(page).hasURL("https://ygoprodeck.com/card-database/?attribute=DIVINE&race=Aqua&num=24&offset=0");
    }

}