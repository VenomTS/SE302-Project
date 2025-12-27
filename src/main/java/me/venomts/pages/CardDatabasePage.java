package me.venomts.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.Map;

public class CardDatabasePage
{
    private Page page;
    /*private Locator _toggleSearchFilters;
    private Locator _filterType;
    private Locator _filterAttribute;
    private Locator _filterRace;
    private Locator _filterArchetype;
    private Locator _filterRarity;
    private Locator _filterFormat;
    private Locator _filterEffect;*/

    public enum FilterMenus
    {
        _filterType,
        _filterAttribute,
        _filterRace,
        _filterArchetype,
        _filterRarity,
        _filterFormat,
        _filterEffect,
    }


    public CardDatabasePage(Page page)
    {
        this.page = page;
    }

    public final Map<FilterMenus, Locator> FilterDropdownMenus = Map.of(
            FilterMenus._filterType, page.locator("#filter-type"),
            FilterMenus._filterAttribute, page.locator("#filter-attribute"),
            FilterMenus._filterRace, page.locator("#filter-race"),
            FilterMenus._filterArchetype, page.locator("#filter-archetype"),
            FilterMenus._filterRarity, page.locator("#filter-rarity"),
            FilterMenus._filterFormat, page.locator("#filter-format"),
            FilterMenus._filterEffect, page.locator("#filter-effect")
        );

    public void selectFilterMenu(FilterMenus menu, String option)
    {
        Locator dropdownMenu = FilterDropdownMenus.get(menu);
        dropdownMenu.click();
        page.getByText(option, new Page.GetByTextOptions().setExact(true)).click();
        SafetyPause();
    }

    public void SafetyPause()
    {
        page.waitForSelector(".grid-image");
    }

    //AI helped here, will have to check if it works later:
    public void assertCardMatchFilter(String dataAttribute, String expectedValue) {
        Locator cards = page.locator(".grid-image");
        int count = cards.count();

        for (int i = 0; i < count; i++) {
            String actual = cards.nth(i).getAttribute(dataAttribute);
        }
    }

}